package sk.mimac.benchroom.connector.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.mimac.benchroom.api.dto.impl.BenchmarkParameterDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.service.BenchmarkParameterService;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.connector.ConnectorConstants;
import sk.mimac.benchroom.connector.controller.model.RunData;

/**
 *
 * @author Milan Fabian
 */
@RestController
public class BenchmarkDataController {

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private BenchmarkSuiteService benchmarkSuiteService;

    @Autowired
    private BenchmarkParameterService benchmarkParameterService;

    @RequestMapping(value = ConnectorConstants.URL_BENCHMARK_DATA, method = RequestMethod.GET)
    public RunData getBenchmarkData(@RequestParam("id") String dataId) {
        String[] parts = dataId.split("-");
        SoftwareVersionDto version = softwareService.getVersionById(Long.parseLong(parts[0]));
        BenchmarkSuiteDto suite = benchmarkSuiteService.getSuiteById(Long.parseLong(parts[1]));
        List<BenchmarkParameterDto> parameters = benchmarkParameterService.getParametersForSuite(suite.getId());
        RunData runData = new RunData();
        runData.setRunName(suite.getName());
        runData.setRunId(dataId);
        runData.setSofwareSetup(version.getSetupScript());
        runData.setSofwareCleanup(version.getCleanupScript());
        runData.setBenchmarkSetup(suite.getSetupScript());
        runData.setBenchmarkCleanup(suite.getCleanupScript());
        runData.setParameters(prepareRunParameters(parameters, dataId));
        return runData;
    }

    private Map<String, RunData.RunParameter> prepareRunParameters(List<BenchmarkParameterDto> parameters, String dataId) {
        Map<String, RunData.RunParameter> result = new HashMap<>();
        for (BenchmarkParameterDto parameter : parameters) {
            RunData.RunParameter runParam = new RunData.RunParameter();
            runParam.setCommandLineArguments(parameter.getCommandLineArguments());
            runParam.setCommandLineInput(parameter.getCommandLineInput());
            result.put(dataId + "-" + parameter.getId(), runParam);
        }
        return result;
    }
}
