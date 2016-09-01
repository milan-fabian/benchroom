package sk.mimac.benchroom.web.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.filter.BenchmarkRunFilter;
import sk.mimac.benchroom.api.filter.BenchmarkSuiteFilter;
import sk.mimac.benchroom.api.filter.SoftwareFilter;
import sk.mimac.benchroom.api.filter.SoftwareVersionFilter;
import sk.mimac.benchroom.api.service.BenchmarkParameterService;
import sk.mimac.benchroom.api.service.BenchmarkRunService;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.web.PageWrapper;
import sk.mimac.benchroom.web.ValueLabelWrapper;
import sk.mimac.benchroom.web.WebConstants;
import sk.mimac.benchroom.web.utils.FilterUtils;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class BenchmarkController {

    public static final int SEARCH_MAX_RESULTS = 10;

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private BenchmarkSuiteService benchmarkSuiteService;

    @Autowired
    private BenchmarkParameterService benchmarkParameterService;

    @Autowired
    private BenchmarkRunService benchmarkRunService;

    @RequestMapping(value = WebConstants.URL_BENCHMARK, method = RequestMethod.GET)
    public ModelAndView getBenchmark() {
        return new ModelAndView("benchmark/benchmark_list");
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_BENCHMARK_SEARCH_SOFTWARE, method = RequestMethod.GET)
    public List<ValueLabelWrapper> getBenchmarkSearchSoftware(@RequestParam("term") String fulltext) {
        SoftwareFilter filter = new SoftwareFilter();
        filter.setPageNumber(1);
        filter.setPageSize(SEARCH_MAX_RESULTS);
        filter.setFulltext(fulltext);
        List<ValueLabelWrapper> result = new ArrayList<>();
        for (SoftwareDto software : softwareService.getSoftwarePage(filter).getElements()) {
            result.add(new ValueLabelWrapper(software.getId(), software.getName()));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_BENCHMARK_SEARCH_VERSION, method = RequestMethod.GET)
    public List<ValueLabelWrapper> getBenchmarkSearchVersion(@RequestParam("term") String fulltext, @RequestParam("software") long softwareId) {
        SoftwareVersionFilter filter = new SoftwareVersionFilter();
        filter.setPageNumber(1);
        filter.setPageSize(SEARCH_MAX_RESULTS);
        filter.setFulltext(fulltext);
        filter.setSoftwareId(softwareId);
        List<ValueLabelWrapper> result = new ArrayList<>();
        for (SoftwareVersionDto version : softwareService.getVersionPage(filter).getElements()) {
            result.add(new ValueLabelWrapper(version.getId(), version.getName()));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_BENCHMARK_SEARCH_SUITE, method = RequestMethod.GET)
    public List<ValueLabelWrapper> getBenchmarkSearchSuite(@RequestParam("term") String fulltext, @RequestParam("software") long softwareId) {
        BenchmarkSuiteFilter filter = new BenchmarkSuiteFilter();
        filter.setPageNumber(1);
        filter.setPageSize(SEARCH_MAX_RESULTS);
        filter.setFulltext(fulltext);
        filter.setSoftwareId(softwareId);
        List<ValueLabelWrapper> result = new ArrayList<>();
        for (BenchmarkSuiteDto suite : benchmarkSuiteService.getSuitePage(filter).getElements()) {
            result.add(new ValueLabelWrapper(suite.getId(), suite.getName()));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_BENCHMARK_COUNT_PARAMETERS, method = RequestMethod.GET)
    public long getBenchmarkCountParameters(@RequestParam("suite") long suiteId, @RequestParam("minPriority") short minPriority) {
        return benchmarkParameterService.getParametersCountForSuitePriority(suiteId, minPriority);
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_BENCHMARK_LIST, method = RequestMethod.POST)
    public PageWrapper getBenchmarkList(@RequestParam("suite") long suiteId, @RequestParam("version") long versionId, @Valid DataTablesInput input) {
        BenchmarkRunFilter filter = new BenchmarkRunFilter();
        filter.setBenchmarkSuiteId(suiteId);
        filter.setSoftwareVersionId(versionId);
        FilterUtils.setDataTableToFilter(input, filter);
        return new PageWrapper(benchmarkRunService.getRunPageSimple(filter));
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_DETAIL, method = RequestMethod.GET)
    public ModelAndView getBenchmarkDetail(@RequestParam("run") long runId) {
        Map<String, Object> model = new HashMap<>();
        BenchmarkRunDto run = benchmarkRunService.getRunById(runId);
        BenchmarkSuiteDto suite = benchmarkSuiteService.getSuiteById(run.getBenchmarkSuiteId());
        Object[] params = run.getBenchmarkParameters().stream().map(x -> x.getCommandLineArguments()).toArray();
        model.put("run", run);
        model.put("suite", suite);
        model.put("cmd", MessageFormat.format(suite.getCommandLineArguments(), params));
        return new ModelAndView("benchmark/benchmark_detail", model);
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_COMPARE, method = RequestMethod.GET)
    public ModelAndView getBenchmarkCompare(@RequestParam("run") long runId) {
        Map<String, Object> model = new HashMap<>();
        BenchmarkRunDto run = benchmarkRunService.getRunById(runId);
        Collections.sort(run.getResults());
        model.put("run", run);
        model.put("suite", benchmarkSuiteService.getSuiteById(run.getBenchmarkSuiteId()));
        model.put("sameSystem", getSameSystemRuns(run));
        return new ModelAndView("benchmark/benchmark_compare", model);
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_COMPARE_GRAPH, method = RequestMethod.GET)
    public ModelAndView getBenchmarkCompareGraph(@RequestParam("run") long runId, @RequestParam("width") int width, @RequestParam("height") int height) {
        Map<String, Object> model = new HashMap<>();
        BenchmarkRunDto run = benchmarkRunService.getRunById(runId);
        Collections.sort(run.getResults());
        List<BenchmarkRunDto> sameSystemRuns = getSameSystemRuns(run);
        model.put("run", run);
        model.put("sameSystem", sameSystemRuns);
        model.put("maxResults", getMaxResults(run, sameSystemRuns));
        model.put("width", width);
        model.put("height", height);
        return new ModelAndView("benchmark/benchmark_compare_graph", model);
    }

    private double[] getMaxResults(BenchmarkRunDto run, List<BenchmarkRunDto> sameSystemRuns) {
        double[] maxResults = new double[run.getResults().size()];
        for (int i = 0; i < maxResults.length; i++) {
            for (BenchmarkRunDto run2 : sameSystemRuns) {
                double value = run2.getResults().get(i).getResult();
                if (value > maxResults[i]) {
                    maxResults[i] = value;
                }
            }
        }
        return maxResults;
    }

    private List<BenchmarkRunDto> getSameSystemRuns(BenchmarkRunDto run) {
        BenchmarkRunFilter sameSystemFilter = new BenchmarkRunFilter();
        sameSystemFilter.setBenchmarkSuiteId(run.getBenchmarkSuiteId());
        sameSystemFilter.setSoftwareVersionId(run.getSoftwareVersion().getId());
        sameSystemFilter.setParameters(run.getSystemParameters());
        List<BenchmarkRunDto> sameSystemRuns = benchmarkRunService.getRunPage(sameSystemFilter).getElements();
        sameSystemRuns.forEach(x -> Collections.sort(x.getResults()));
        sameSystemRuns.forEach(x -> Collections.sort(x.getBenchmarkParameters()));
        return sameSystemRuns;
    }
}
