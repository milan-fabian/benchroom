package sk.mimac.benchroom.connector.controller.model;

import java.util.List;
import sk.mimac.benchroom.api.enums.MonitorType;

/**
 *
 * @author Milan Fabian
 */
public class RunInput {

    private String runId;
    private String runName;
    private String sofwareSetup;
    private String sofwareCleanup;
    private String benchmarkSetup;
    private String bencharkAfterEachRunScript;
    private String benchmarkCleanup;
    private String commandLineArguments;
    private List<List<RunParameter>> parameters;
    private List<RunMonitor> monitors;

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getRunName() {
        return runName;
    }

    public void setRunName(String runName) {
        this.runName = runName;
    }

    public String getSofwareSetup() {
        return sofwareSetup;
    }

    public void setSofwareSetup(String sofwareSetup) {
        this.sofwareSetup = sofwareSetup;
    }

    public String getSofwareCleanup() {
        return sofwareCleanup;
    }

    public void setSofwareCleanup(String sofwareCleanup) {
        this.sofwareCleanup = sofwareCleanup;
    }

    public String getBenchmarkSetup() {
        return benchmarkSetup;
    }

    public void setBenchmarkSetup(String benchmarkSetup) {
        this.benchmarkSetup = benchmarkSetup;
    }

    public String getBenchmarkCleanup() {
        return benchmarkCleanup;
    }

    public void setBenchmarkCleanup(String benchmarkCleanup) {
        this.benchmarkCleanup = benchmarkCleanup;
    }

    public String getBencharkAfterEachRunScript() {
        return bencharkAfterEachRunScript;
    }

    public void setBencharkAfterEachRunScript(String bencharkAfterEachRunScript) {
        this.bencharkAfterEachRunScript = bencharkAfterEachRunScript;
    }

    public List<List<RunParameter>> getParameters() {
        return parameters;
    }

    public void setParameters(List<List<RunParameter>> parameters) {
        this.parameters = parameters;
    }

    public List<RunMonitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<RunMonitor> monitors) {
        this.monitors = monitors;
    }

    public String getCommandLineArguments() {
        return commandLineArguments;
    }

    public void setCommandLineArguments(String commandLineArguments) {
        this.commandLineArguments = commandLineArguments;
    }

    public static class RunParameter {

        private long parameterId;
        private String parameterName;
        private String commandLineArguments;
        private String setupScript;
        private String cleanupScript;

        public long getParameterId() {
            return parameterId;
        }

        public void setParameterId(long parameterId) {
            this.parameterId = parameterId;
        }

        public String getParameterName() {
            return parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getCommandLineArguments() {
            return commandLineArguments;
        }

        public void setCommandLineArguments(String commandLineArguments) {
            this.commandLineArguments = commandLineArguments;
        }

        public String getCleanupScript() {
            return cleanupScript;
        }

        public void setCleanupScript(String cleanupScript) {
            this.cleanupScript = cleanupScript;
        }

        public String getSetupScript() {
            return setupScript;
        }

        public void setSetupScript(String setupScript) {
            this.setupScript = setupScript;
        }
    }

    public static class RunMonitor {

        private long monitorId;
        private MonitorType type;
        private String action;

        public long getMonitorId() {
            return monitorId;
        }

        public void setMonitorId(long monitorId) {
            this.monitorId = monitorId;
        }

        public MonitorType getType() {
            return type;
        }

        public void setType(MonitorType type) {
            this.type = type;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

    }
}
