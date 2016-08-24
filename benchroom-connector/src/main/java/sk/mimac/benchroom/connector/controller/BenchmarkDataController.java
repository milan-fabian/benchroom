package sk.mimac.benchroom.connector.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.mimac.benchroom.api.dto.impl.BenchmarkParameterDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.enums.Platform;
import sk.mimac.benchroom.api.enums.ScriptType;
import sk.mimac.benchroom.api.service.BenchmarkParameterService;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.api.service.ScriptService;
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

    @Autowired
    private ScriptService scriptService;

    @RequestMapping(value = ConnectorConstants.URL_BENCHMARK_DATA, method = RequestMethod.GET)
    public RunData getBenchmarkData(@RequestParam("id") String dataId, @RequestParam("platform") Platform platform) {
        String[] parts = dataId.split("-");
        SoftwareVersionDto version = softwareService.getVersionById(Long.parseLong(parts[0]));
        BenchmarkSuiteDto suite = benchmarkSuiteService.getSuiteById(Long.parseLong(parts[1]));
        List<BenchmarkParameterDto> parameters = benchmarkParameterService.getParametersForSuite(suite.getId());
        String setupScript = scriptService.getScriptForPlatformVersion(platform, version.getId(), ScriptType.SETUP);
        String cleanupScript = scriptService.getScriptForPlatformVersion(platform, version.getId(), ScriptType.CLEANUP);
        RunData runData = new RunData();
        runData.setRunName(version.getSoftwareName() + " " + version.getName() + " - " + suite.getName());
        runData.setRunId(dataId);
        runData.setSofwareSetup(setupScript);
        runData.setSofwareCleanup(cleanupScript);
        runData.setBenchmarkSetup(suite.getSetupScript());
        runData.setBenchmarkCleanup(suite.getCleanupScript());
        runData.setParameters(prepareRunParameters(parameters, dataId));
        return runData;
    }

    private List<RunData.RunParameter> prepareRunParameters(List<BenchmarkParameterDto> parameters, String dataId) {
        List<RunData.RunParameter> result = new ArrayList<>();
        for (BenchmarkParameterDto parameter : parameters) {
            RunData.RunParameter runParam = new RunData.RunParameter();
            runParam.setParameterId(dataId + "-" + parameter.getId());
            runParam.setCommandLineArguments(parameter.getCommandLineArguments());
            runParam.setCommandLineInput(parameter.getCommandLineInput());
            result.add(runParam);
        }
        return result;
    }
}
