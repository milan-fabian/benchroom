package sk.mimac.benchroom.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sk.mimac.benchroom.api.dto.impl.BenchmarkParameterDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.filter.BenchmarkParameterFilter;
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
import sk.mimac.benchroom.web.export.ExcelExporter;
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
    public ModelAndView getBenchmarkCompare(@RequestParam("run") long runId, @RequestParam(name = "parameters", required = false) List<Long> parameterIds) {
        Map<String, Object> model = new HashMap<>();
        BenchmarkRunDto run = benchmarkRunService.getRunById(runId);
        Collections.sort(run.getResults());
        model.put("run", run);
        model.put("suite", benchmarkSuiteService.getSuiteById(run.getBenchmarkSuiteId()));
        List<BenchmarkRunDto> runs = getSameSystemRuns(run, parameterIds);
        model.put("sameSystem", runs);
        
        if (parameterIds == null) {
            parameterIds = runs.stream().map(r -> r.getBenchmarkParameters()).flatMap(List::stream).map(p -> p.getId()).distinct().collect(Collectors.toList());
        }
        model.put("choosenParameters", parameterIds);
        model.put("parameters", getAllParametersForSuite(run.getBenchmarkSuiteId()));
        return new ModelAndView("benchmark/benchmark_compare", model);
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_BENCHMARK_COMPARE_EXCEL, method = RequestMethod.GET)
    public void getBenchmarkCompareExcel(HttpServletResponse response, @RequestParam("runs") List<Long> runIds) throws IOException {
        List<BenchmarkRunDto> runs = benchmarkRunService.getRunsByIds(runIds);
        runs.forEach(run -> Collections.sort(run.getResults()));
        runs.forEach(run -> Collections.sort(run.getBenchmarkParameters()));
        BenchmarkSuiteDto suite = benchmarkSuiteService.getSuiteById(runs.get(0).getBenchmarkSuiteId());
        ExcelExporter excelExporter = new ExcelExporter(runs, suite.getName());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Benchroom-results.xlsx");
        try (OutputStream outputStream = response.getOutputStream()) {
            excelExporter.export(outputStream);
            outputStream.flush();
        }
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_COMPARE_GRAPH, method = RequestMethod.GET)
    public ModelAndView getBenchmarkCompareGraph(@RequestParam("runs") List<Long> runIds, @RequestParam("monitors") List<Long> monitorIds,
            @RequestParam("width") int width, @RequestParam("height") int height) {
        Map<String, Object> model = new HashMap<>();
        List<BenchmarkRunDto> runs = benchmarkRunService.getRunsByIds(runIds);
        runs.forEach(run -> run.getResults().removeIf(r -> !monitorIds.contains(r.getMonitorId())));
        runs.forEach(run -> Collections.sort(run.getResults()));
        runs.forEach(run -> Collections.sort(run.getBenchmarkParameters()));
        model.put("run", runs.get(0));
        model.put("sameSystem", runs);
        model.put("maxResults", getMaxResults(runs));
        model.put("width", width);
        model.put("height", height);
        return new ModelAndView("benchmark/benchmark_compare_graph", model);
    }

    private double[] getMaxResults(List<BenchmarkRunDto> runs) {
        double[] maxResults = new double[runs.get(0).getResults().size()];
        for (int i = 0; i < maxResults.length; i++) {
            for (BenchmarkRunDto run : runs) {
                double value = run.getResults().get(i).getResult();
                if (value > maxResults[i]) {
                    maxResults[i] = value;
                }
            }
        }
        return maxResults;
    }
    
    private List<BenchmarkParameterDto> getAllParametersForSuite(long suiteId) {
        BenchmarkParameterFilter filter = new BenchmarkParameterFilter();
        filter.setPageSize(Integer.MAX_VALUE);
        filter.setSuiteId(suiteId);
        return benchmarkParameterService.getParameterPage(filter).getElements();
    }

    private List<BenchmarkRunDto> getSameSystemRuns(BenchmarkRunDto run, List<Long> parameterIds) {
        BenchmarkRunFilter sameSystemFilter = new BenchmarkRunFilter();
        sameSystemFilter.setBenchmarkSuiteId(run.getBenchmarkSuiteId());
        sameSystemFilter.setBenchmarkParameterIds(parameterIds);
        sameSystemFilter.setSoftwareVersionId(run.getSoftwareVersion().getId());
        sameSystemFilter.setSystemInfoId(run.getSystemInfo().getId());
        List<BenchmarkRunDto> sameSystemRuns = benchmarkRunService.getRunPage(sameSystemFilter).getElements();
        sameSystemRuns.forEach(x -> Collections.sort(x.getResults()));
        sameSystemRuns.forEach(x -> Collections.sort(x.getBenchmarkParameters()));
        return sameSystemRuns;
    }
}
