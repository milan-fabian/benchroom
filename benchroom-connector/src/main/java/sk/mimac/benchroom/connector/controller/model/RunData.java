package sk.mimac.benchroom.connector.controller.model;

import java.util.Map;

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

    private Map<String, RunParameter> parameters;

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

    public Map<String, RunParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, RunParameter> parameters) {
        this.parameters = parameters;
    }

    public static class RunParameter {

        private String commandLineArguments;

        private String commandLineInput;

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
}
