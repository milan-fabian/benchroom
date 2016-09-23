package sk.mimac.benchroom.web.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;
import sk.mimac.benchroom.api.filter.BenchmarkSuiteFilter;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.web.PageWrapper;
import sk.mimac.benchroom.web.WebConstants;
import sk.mimac.benchroom.web.utils.FilterUtils;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class BenchmarkSuiteController {

    @Autowired
    private BenchmarkSuiteService benchmarkSuiteService;

    @Autowired
    private SoftwareService softwareService;

    @RequestMapping(value = WebConstants.URL_BENCHMARK_SUITE, method = RequestMethod.GET)
    public ModelAndView getBenchmarkSuite(@RequestParam("software") long softwareId) {
        Map<String, Object> model = new HashMap<>();
        model.put("software", softwareService.getSoftwareById(softwareId));
        return new ModelAndView("benchmark_suite/benchmark_suite_list", model);
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_SUITE_EDIT, method = RequestMethod.GET)
    public ModelAndView getBenchmarkSuiteEdit(@RequestParam(name = "suite", required = false) Long suiteId, @RequestParam("software") long softwareId) {
        Map<String, Object> model = new HashMap<>();
        if (suiteId != null) {
            model.put("suite", benchmarkSuiteService.getSuiteById(suiteId));
        } else {
            model.put("suite", new BenchmarkSuiteDto());
        }
        model.put("software", softwareService.getSoftwareById(softwareId));
        return new ModelAndView("benchmark_suite/benchmark_suite_edit", model);
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_SUITE_EDIT, method = RequestMethod.POST)
    public ModelAndView postBencharkSuiteEdit(@Valid @ModelAttribute(name = "suite") BenchmarkSuiteDto suite, BindingResult result,
            @RequestParam("software") long softwareId) {
        if (result.hasErrors()) {
            Map<String, Object> model = new HashMap<>();
            model.put("software", softwareService.getSoftwareById(softwareId));
            return new ModelAndView("benchmark_suite/benchmark_suite_edit", model);
        }
        suite.setSoftwareId(softwareId);
        suite.getParameterNames().removeIf(name -> name.trim().isEmpty());
        if (suite.getId() == null) {
            benchmarkSuiteService.insertSuite(suite);
        } else {
            benchmarkSuiteService.updateSuite(suite);
        }
        return new ModelAndView("redirect:" + WebConstants.URL_BENCHMARK_SUITE + "?software=" + softwareId);
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_BENCHMARK_SUITE_LIST, method = RequestMethod.GET)
    public PageWrapper getBenchmarkSuiteList(@RequestParam("software") long softwareId, @Valid DataTablesInput input) {
        BenchmarkSuiteFilter filter = new BenchmarkSuiteFilter();
        filter.setSoftwareId(softwareId);
        FilterUtils.setDataTableToFilter(input, filter);
        return new PageWrapper(benchmarkSuiteService.getSuitePage(filter));
    }
}
