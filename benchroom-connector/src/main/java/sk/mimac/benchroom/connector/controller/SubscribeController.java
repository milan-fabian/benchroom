package sk.mimac.benchroom.connector.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunJobDto;
import sk.mimac.benchroom.api.enums.JobStatus;
import sk.mimac.benchroom.api.service.ExecutorSubscriptionService;
import sk.mimac.benchroom.api.system.SystemParameter;
import sk.mimac.benchroom.connector.ConnectorConstants;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class SubscribeController {

    @Autowired
    private ExecutorSubscriptionService executorSubscriptionService;

    @ResponseBody
    @RequestMapping(value = ConnectorConstants.URL_SUBSCRIBE_CHECK, method = RequestMethod.POST)
    public BenchmarkRunJobDto postSubscribeCheck(@RequestBody Map<SystemParameter, String> systemParameters) {
        BenchmarkRunJobDto runJob = executorSubscriptionService.checkRunJob(systemParameters);
        if (runJob != null) {
            return runJob;
        }
        return new BenchmarkRunJobDto();
    }

    @ResponseBody
    @RequestMapping(value = ConnectorConstants.URL_SUBSCRIBE_CHANGE, method = RequestMethod.GET)
    public void getSubscribeChange(@RequestParam("runJob") long runJobId, @RequestParam("status") JobStatus jobStatus) {
        executorSubscriptionService.changeRunJobStatus(runJobId, jobStatus);
    }

}
