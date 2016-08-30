package sk.mimac.benchroom.connector.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.mimac.benchroom.api.dto.impl.BenchmarkMonitorDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkParameterDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.enums.Platform;
import sk.mimac.benchroom.api.enums.ScriptType;
import sk.mimac.benchroom.api.service.BenchmarkMonitorService;
import sk.mimac.benchroom.api.service.BenchmarkParameterService;
import sk.mimac.benchroom.api.service.BenchmarkRunService;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.api.service.ScriptService;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.connector.ConnectorConstants;
import sk.mimac.benchroom.connector.controller.model.RunData;
import sk.mimac.benchroom.connector.controller.model.Run;

/**
 *
 * @author Milan Fabian
 */
@RestController
public class Controller {

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private BenchmarkSuiteService benchmarkSuiteService;

    @Autowired
    private BenchmarkParameterService benchmarkParameterService;

    @Autowired
    private BenchmarkMonitorService benchmarkMonitorService;

    @Autowired
    private BenchmarkRunService benchmarkRunService;

    @Autowired
    private ScriptService scriptService;

    @RequestMapping(value = ConnectorConstants.URL_BENCHMARK_DATA, method = RequestMethod.GET)
    public RunData getBenchmarkData(@RequestParam("id") String dataId, @RequestParam("platform") Platform platform, @RequestParam("minPriority") short minPriority) {
        String[] parts = dataId.split("-");
        SoftwareVersionDto version = softwareService.getVersionById(Long.parseLong(parts[0]));
        BenchmarkSuiteDto suite = benchmarkSuiteService.getSuiteById(Long.parseLong(parts[1]));
        List<List<RunData.RunParameter>> runParameters = new ArrayList<>(suite.getParameterPositions());
        for (short i = 0; i < suite.getParameterPositions(); i++) {
            List<BenchmarkParameterDto> parameters = benchmarkParameterService.getParametersForSuitePositionPriority(suite.getId(), i, minPriority);
            runParameters.add(convertRunParameters(parameters));
        }
        List<BenchmarkMonitorDto> monitors = benchmarkMonitorService.getMonitorsForSuite(suite.getId());
        String setupScript = scriptService.getScriptForPlatformVersion(platform, version.getId(), ScriptType.SETUP);
        String cleanupScript = scriptService.getScriptForPlatformVersion(platform, version.getId(), ScriptType.CLEANUP);
        List<RunData.RunMonitor> runMonitors = prepareRunMonitors(monitors);
        return getRunData(version, suite, dataId, setupScript, cleanupScript, runParameters, runMonitors);
    }

    @RequestMapping(value = ConnectorConstants.URL_BENCHMARK_RESULT, method = RequestMethod.POST)
    public void postBenchmarkResult(@RequestBody Run run) {
        String[] ids = run.getRunId().split("-");
        BenchmarkRunDto dto = new BenchmarkRunDto();
        dto.setSoftwareVersion(new SoftwareVersionDto(Long.parseLong(ids[0])));
        dto.setBenchmarkParameters(run.getParameterIds().stream().map(id -> new BenchmarkParameterDto(id)).collect(Collectors.toList()));
        dto.setSystemParameters(run.getSystemParameters());
        dto.setWhenStarted(run.getWhenStarted());
        dto.setBenchmarkSuiteId(Long.parseLong(ids[1]));
        
        Map<Long, Double> results = new HashMap<>();
        for (Run.RunResult runResult : run.getResults()) {
            results.put(runResult.getMonitorId(), runResult.getResult());
        }
        benchmarkRunService.insertRun(dto, results);
    }

    private RunData getRunData(SoftwareVersionDto version, BenchmarkSuiteDto suite, String dataId, String setupScript,
            String cleanupScript, List<List<RunData.RunParameter>> parameters, List<RunData.RunMonitor> monitors) {
        RunData runData = new RunData();
        runData.setRunName(version.getSoftwareName() + " " + version.getName() + " - " + suite.getName());
        runData.setRunId(dataId);
        runData.setSofwareSetup(setupScript);
        runData.setSofwareCleanup(cleanupScript);
        runData.setBenchmarkSetup(suite.getSetupScript());
        runData.setBenchmarkCleanup(suite.getCleanupScript());
        runData.setParameters(parameters);
        runData.setMonitors(monitors);
        runData.setCommandLineArguments(suite.getCommandLineArguments());
        return runData;
    }

    private List<RunData.RunParameter> convertRunParameters(List<BenchmarkParameterDto> parameters) {
        List<RunData.RunParameter> result = new ArrayList<>();
        for (BenchmarkParameterDto parameter : parameters) {
            RunData.RunParameter runParam = new RunData.RunParameter();
            runParam.setParameterId(parameter.getId());
            runParam.setParameterName(parameter.getName());
            runParam.setCommandLineArguments(parameter.getCommandLineArguments());
            result.add(runParam);
        }
        return result;
    }

    private List<RunData.RunMonitor> prepareRunMonitors(List<BenchmarkMonitorDto> monitors) {
        List<RunData.RunMonitor> result = new ArrayList<>();
        for (BenchmarkMonitorDto monitor : monitors) {
            RunData.RunMonitor runParam = new RunData.RunMonitor();
            runParam.setMonitorId(monitor.getId());
            runParam.setType(monitor.getType());
            runParam.setAction(monitor.getAction());
            result.add(runParam);
        }
        return result;
    }
}
