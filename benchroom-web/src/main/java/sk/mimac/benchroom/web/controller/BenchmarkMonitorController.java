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
import sk.mimac.benchroom.api.dto.impl.BenchmarkMonitorDto;
import sk.mimac.benchroom.api.filter.BenchmarkMonitorFilter;
import sk.mimac.benchroom.api.service.BenchmarkMonitorService;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.web.PageWrapper;
import sk.mimac.benchroom.web.WebConstants;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class BenchmarkMonitorController {

    @Autowired
    private BenchmarkMonitorService benchmarkMonitorService;

    @Autowired
    private BenchmarkSuiteService benchmarkSuiteService;

    @RequestMapping(value = WebConstants.URL_BENCHMARK_MONITOR, method = RequestMethod.GET)
    public ModelAndView getBenchmarkMonitor(@RequestParam("suite") long suiteId) {
        Map<String, Object> model = new HashMap<>();
        model.put("suite", benchmarkSuiteService.getSuiteById(suiteId));
        return new ModelAndView("benchmark_monitor/benchmark_monitor_list", model);
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_MONITOR_EDIT, method = RequestMethod.GET)
    public ModelAndView getBenchmarkMonitorEdit(@RequestParam(name = "monitor", required = false) Long monitorId, @RequestParam("suite") long suiteId) {
        Map<String, Object> model = new HashMap<>();
        if (monitorId != null) {
            model.put("monitor", benchmarkMonitorService.getMonitorById(monitorId));
        } else {
            model.put("monitor", new BenchmarkMonitorDto());
        }
        model.put("suite", benchmarkSuiteService.getSuiteById(suiteId));
        return new ModelAndView("benchmark_monitor/benchmark_monitor_edit", model);
    }

    @RequestMapping(value = WebConstants.URL_BENCHMARK_MONITOR_EDIT, method = RequestMethod.POST)
    public ModelAndView postBencharkMonitorEdit(@Valid @ModelAttribute(name = "monitor") BenchmarkMonitorDto monitor, BindingResult result,
            @RequestParam("suite") long suiteId) {
        if (result.hasErrors()) {
            Map<String, Object> model = new HashMap<>();
            model.put("suite", benchmarkSuiteService.getSuiteById(suiteId));
            return new ModelAndView("benchmark_monitor/benchmark_monitor_edit", model);
        }
        monitor.setBenchmarkSuiteId(suiteId);
        if (monitor.getId() == null) {
            benchmarkMonitorService.insertMonitor(monitor);
        } else {
            benchmarkMonitorService.updateMonitor(monitor);
        }
        return new ModelAndView("redirect:" + WebConstants.URL_BENCHMARK_MONITOR + "?suite=" + suiteId);
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_BENCHMARK_MONITOR_LIST, method = RequestMethod.GET)
    public PageWrapper getBenchmarkMonitorList(@RequestParam("suite") long suiteId, @RequestParam("length") int pageSize, @RequestParam("start") int start) {
        BenchmarkMonitorFilter filter = new BenchmarkMonitorFilter();
        filter.setSuiteId(suiteId);
        filter.setPageNumber((start / pageSize) + 1);
        filter.setPageSize(pageSize);
        return new PageWrapper(benchmarkMonitorService.getMonitorPage(filter));
    }
}
