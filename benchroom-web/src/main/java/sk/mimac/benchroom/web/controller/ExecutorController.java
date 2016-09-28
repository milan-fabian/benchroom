package sk.mimac.benchroom.web.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunJobDto;
import sk.mimac.benchroom.api.service.ExecutorSubscriptionService;
import sk.mimac.benchroom.web.WebConstants;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class ExecutorController {

    @Autowired
    private ExecutorSubscriptionService executorSubscriptionService;

    @RequestMapping(value = WebConstants.URL_EXECUTOR, method = RequestMethod.GET)
    public ModelAndView getExecutor() {
        Map<String, Object> model = new HashMap<>();
        model.put("executors", executorSubscriptionService.getAllExecutors());
        return new ModelAndView("executor/executor_list", model);
    }

    @RequestMapping(value = WebConstants.URL_EXECUTOR_DETAIL, method = RequestMethod.GET)
    public ModelAndView getExecutorDetail(@RequestParam("executor") long executorId) {
        Map<String, Object> model = new HashMap<>();
        model.put("executor", executorSubscriptionService.getExecutorById(executorId));
        return new ModelAndView("executor/executor_detail", model);
    }

    @RequestMapping(value = WebConstants.URL_EXECUTOR_ADD_JOB, method = RequestMethod.POST)
    public ModelAndView postExecutorAddJob(@RequestParam("version") long versionId, @RequestParam("suite") long suiteId,
            @RequestParam("executor") long executorId, @RequestParam("runs") int numberOfRuns, @RequestParam("minPriority") short minPriority) {
        BenchmarkRunJobDto runJob = new BenchmarkRunJobDto();
        runJob.setMinPriority(minPriority);
        runJob.setNumberOfRuns(numberOfRuns);
        runJob.setBenchmarkSuiteId(suiteId);
        runJob.setSoftwareVersionId(versionId);
        runJob.setNumberOfRuns(numberOfRuns);
        executorSubscriptionService.addRunJob(executorId, runJob);
        return new ModelAndView("redirect:" + WebConstants.URL_EXECUTOR_DETAIL + "?executor=" + executorId);
    }

}
