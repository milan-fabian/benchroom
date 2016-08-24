package sk.mimac.benchroom.web.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.filter.BenchmarkSuiteFilter;
import sk.mimac.benchroom.api.filter.SoftwareFilter;
import sk.mimac.benchroom.api.filter.SoftwareVersionFilter;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.web.ValueLabelWrapper;
import sk.mimac.benchroom.web.WebConstants;

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
        List<ValueLabelWrapper> result = new ArrayList<>();
        for (BenchmarkSuiteDto suite : benchmarkSuiteService.getSuitePage(filter).getElements()) {
            result.add(new ValueLabelWrapper(suite.getId(), suite.getName()));
        }
        return result;
    }
}
