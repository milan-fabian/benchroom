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
import sk.mimac.benchroom.api.dto.impl.SystemInfoDto;
import sk.mimac.benchroom.api.enums.Platform;
import sk.mimac.benchroom.api.enums.ScriptType;
import sk.mimac.benchroom.api.filter.BenchmarkRunFilter;
import sk.mimac.benchroom.api.service.BenchmarkMonitorService;
import sk.mimac.benchroom.api.service.BenchmarkParameterService;
import sk.mimac.benchroom.api.service.BenchmarkRunService;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.api.service.ScriptService;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.api.service.SystemInfoService;
import sk.mimac.benchroom.api.system.SystemParameter;
import sk.mimac.benchroom.connector.ConnectorConstants;
import sk.mimac.benchroom.connector.controller.model.RunInput;
import sk.mimac.benchroom.connector.controller.model.RunOutput;

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

    @Autowired
    private SystemInfoService systemInfoService;

    @RequestMapping(value = ConnectorConstants.URL_BENCHMARK_DATA, method = RequestMethod.GET)
    public RunInput getBenchmarkData(@RequestParam("id") String dataId, @RequestParam("platform") Platform platform, @RequestParam("minPriority") short minPriority,
            @RequestParam(name = "choosenParameters", required = false) List<Long> choosenParameters) {
        String[] parts = dataId.split("-");
        SoftwareVersionDto version = softwareService.getVersionById(Long.parseLong(parts[0]));
        BenchmarkSuiteDto suite = benchmarkSuiteService.getSuiteById(Long.parseLong(parts[1]));
        int parameterPositions = suite.getParameterNames().size();
        List<List<RunInput.RunParameter>> runParameters = new ArrayList<>(parameterPositions);
        for (short i = 0; i < parameterPositions; i++) {
            List<BenchmarkParameterDto> parameters = benchmarkParameterService.getParametersForSuitePositionPriority(suite.getId(), i, minPriority);
            if (choosenParameters != null) {
                parameters.removeIf(x -> !choosenParameters.contains(x.getId()));
            }
            runParameters.add(convertRunParameters(parameters));
        }
        List<BenchmarkMonitorDto> monitors = benchmarkMonitorService.getMonitorsForSuite(suite.getId());
        String setupScript = scriptService.getScriptForPlatformVersion(platform, version.getId(), ScriptType.SETUP);
        String cleanupScript = scriptService.getScriptForPlatformVersion(platform, version.getId(), ScriptType.CLEANUP);
        List<RunInput.RunMonitor> runMonitors = prepareRunMonitors(monitors);
        return getRunData(version, suite, dataId, setupScript, cleanupScript, runParameters, runMonitors);
    }

    @RequestMapping(value = ConnectorConstants.URL_BENCHMARK_RESULT, method = RequestMethod.POST)
    public void postBenchmarkResult(@RequestBody RunOutput run) {
        String[] ids = run.getRunId().split("-");
        BenchmarkRunDto dto = new BenchmarkRunDto();
        dto.setSoftwareVersion(new SoftwareVersionDto(Long.parseLong(ids[0])));
        dto.setBenchmarkParameters(run.getParameterIds().stream().map(id -> new BenchmarkParameterDto(id)).collect(Collectors.toList()));
        dto.setSystemInfo(new SystemInfoDto(systemInfoService.getOrCreateInfo(run.getSystemParameters())));
        dto.setWhenStarted(run.getWhenStarted());
        dto.setBenchmarkSuiteId(Long.parseLong(ids[1]));

        Map<Long, Double> results = new HashMap<>();
        for (RunOutput.RunResult runResult : run.getResults()) {
            results.put(runResult.getMonitorId(), runResult.getResult());
        }
        benchmarkRunService.insertRun(dto, results);
    }

    @RequestMapping(value = ConnectorConstants.URL_RUNNED_COMBINATIONS, method = RequestMethod.POST)
    public List<long[]> postRunnedCombinations(@RequestParam("id") String dataId, @RequestParam("platform") Platform platform, @RequestParam("minPriority") short minPriority,
            @RequestParam(name = "choosenParameters", required = false) List<Long> choosenParameters,
            @RequestBody Map<SystemParameter, String> systemParameters) {
        Long systemInfoId = systemInfoService.getInfoIdByParameters(systemParameters);
        if (systemInfoId == null) {
            return new ArrayList<>();
        }
        String[] parts = dataId.split("-");
        BenchmarkSuiteDto suite = benchmarkSuiteService.getSuiteById(Long.parseLong(parts[1]));
        BenchmarkRunFilter runFilter = new BenchmarkRunFilter();
        runFilter.setSystemInfoId(systemInfoId);
        runFilter.setSoftwareVersionId(Long.parseLong(parts[0]));
        runFilter.setBenchmarkSuiteId(suite.getId());
        runFilter.setPageSize(Integer.MAX_VALUE);
        List<BenchmarkRunDto> runs = benchmarkRunService.getRunPage(runFilter).getElements();
        List<long[]> result = new ArrayList<>(runs.size());
        for (BenchmarkRunDto run : runs) {
            long[] temp = new long[suite.getParameterNames().size()];
            for (BenchmarkParameterDto param : run.getBenchmarkParameters()) {
                temp[param.getPosition()] = param.getId();
            }
            result.add(temp);
        }
        return result;
    }

    private RunInput getRunData(SoftwareVersionDto version, BenchmarkSuiteDto suite, String dataId, String setupScript,
            String cleanupScript, List<List<RunInput.RunParameter>> parameters, List<RunInput.RunMonitor> monitors) {
        RunInput runData = new RunInput();
        runData.setRunName(version.getSoftwareName() + " " + version.getName() + " - " + suite.getName());
        runData.setRunId(dataId);
        runData.setSofwareSetup(setupScript);
        runData.setSofwareCleanup(cleanupScript);
        runData.setBenchmarkSetup(suite.getSetupScript());
        runData.setBenchmarkCleanup(suite.getCleanupScript());
        runData.setBencharkAfterEachRunScript(suite.getAfterEachRunScript());
        runData.setParameters(parameters);
        runData.setMonitors(monitors);
        runData.setCommandLineArguments(suite.getCommandLineArguments());
        return runData;
    }

    private List<RunInput.RunParameter> convertRunParameters(List<BenchmarkParameterDto> parameters) {
        List<RunInput.RunParameter> result = new ArrayList<>();
        for (BenchmarkParameterDto parameter : parameters) {
            RunInput.RunParameter runParam = new RunInput.RunParameter();
            runParam.setParameterId(parameter.getId());
            runParam.setParameterName(parameter.getName());
            runParam.setCommandLineArguments(parameter.getCommandLineArguments());
            runParam.setSetupScript(parameter.getSetupScript());
            runParam.setCleanupScript(parameter.getCleanupScript());
            result.add(runParam);
        }
        return result;
    }

    private List<RunInput.RunMonitor> prepareRunMonitors(List<BenchmarkMonitorDto> monitors) {
        List<RunInput.RunMonitor> result = new ArrayList<>();
        for (BenchmarkMonitorDto monitor : monitors) {
            RunInput.RunMonitor runParam = new RunInput.RunMonitor();
            runParam.setMonitorId(monitor.getId());
            runParam.setType(monitor.getType());
            runParam.setAction(monitor.getAction());
            result.add(runParam);
        }
        return result;
    }
}
