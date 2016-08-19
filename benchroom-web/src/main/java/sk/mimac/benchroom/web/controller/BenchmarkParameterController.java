package sk.mimac.benchroom.web.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sk.mimac.benchroom.api.dto.impl.BenchmarkParameterDto;
import sk.mimac.benchroom.api.filter.BenchmarkParameterFilter;
import sk.mimac.benchroom.api.service.BenchmarkParameterService;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.web.PageWrapper;
import sk.mimac.benchroom.web.WebConstants;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class BenchmarkParameterController {

    @Autowired
    private BenchmarkParameterService benchmarkParametersService;

    @Autowired
    private BenchmarkSuiteService benchmarkSuiteService;

    @RequestMapping(value = WebConstants.URL_BENCHMARK_PARAMETER, method = RequestMethod.GET)
    public ModelAndView getBenchmarkParameter(@RequestParam("suite") long suiteId) {
        Map<String, Object> model = new HashMap<>();
        model.put("suite", benchmarkSuiteService.getSuiteById(suiteId));
        return new ModelAndView("benchmark_parameter/benchmark_parameter_list", model);
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_PARAMETER_EDIT, method = RequestMethod.GET)
    public ModelAndView getBenchmarkParameterEdit(@RequestParam(name = "parameter", required = false) Long parameterId, @RequestParam("suite") long suiteId) {
        Map<String, Object> model = new HashMap<>();
        if (parameterId != null) {
            model.put("parameter", benchmarkParametersService.getParameterById(parameterId));
        } else {
            model.put("parameter", new BenchmarkParameterDto());
        }
        model.put("suite", benchmarkSuiteService.getSuiteById(suiteId));
        return new ModelAndView("benchmark_parameter/benchmark_parameter_edit", model);
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_PARAMETER_EDIT, method = RequestMethod.POST)
    public ModelAndView postBencharkParameterEdit(@Valid @ModelAttribute(name = "parameter") BenchmarkParameterDto parameter, BindingResult result,
            @RequestParam("suite") long suiteId) {
        if (result.hasErrors()) {
            Map<String, Object> model = new HashMap<>();
            model.put("suite", benchmarkSuiteService.getSuiteById(suiteId));
            return new ModelAndView("benchmark_parameter/benchmark_parameter_edit", model);
        }
        parameter.setBenchmarkSuiteId(suiteId);
        if (parameter.getId() == null) {
            benchmarkParametersService.insertParameter(parameter);
        } else {
            benchmarkParametersService.updateParameter(parameter);
        }
        return new ModelAndView("redirect:" + WebConstants.URL_BENCHMARK_PARAMETER + "?suite=" + suiteId);
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_BENCHMARK_PARAMETER_LIST, method = RequestMethod.GET)
    public PageWrapper getBenchmarkParameterList(@RequestParam("suite") long suiteId, @RequestParam("length") int pageSize, @RequestParam("start") int start) {
        BenchmarkParameterFilter filter = new BenchmarkParameterFilter();
        filter.setSuiteId(suiteId);
        filter.setPageNumber((start / pageSize) + 1);
        filter.setPageSize(pageSize);
        return new PageWrapper(benchmarkParametersService.getParameterPage(filter));
    }
}
