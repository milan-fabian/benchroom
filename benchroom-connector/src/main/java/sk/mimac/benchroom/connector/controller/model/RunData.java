package sk.mimac.benchroom.connector.controller.model;

import java.util.List;
import sk.mimac.benchroom.api.enums.MonitorType;

/**
 *
 * @author Milan Fabian
 */
public class RunData {

    private String runId;
    private String runName;
    private String sofwareSetup;
    private String sofwareCleanup;
    private String benchmarkSetup;
    private String benchmarkCleanup;
    private List<RunParameter> parameters;
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

    public List<RunParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<RunParameter> parameters) {
        this.parameters = parameters;
    }

    public List<RunMonitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<RunMonitor> monitors) {
        this.monitors = monitors;
    }

    public static class RunParameter {

        private long parameterId;
        private String commandLineArguments;
        private String commandLineInput;

        public long getParameterId() {
            return parameterId;
        }

        public void setParameterId(long parameterId) {
            this.parameterId = parameterId;
        }

        public String getCommandLineArguments() {
            return commandLineArguments;
        }

        public void setCommandLineArguments(String commandLineArguments) {
            this.commandLineArguments = commandLineArguments;
        }

        public String getCommandLineInput() {
            return commandLineInput;
        }

        public void setCommandLineInput(String commandLineInput) {
            this.commandLineInput = commandLineInput;
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
