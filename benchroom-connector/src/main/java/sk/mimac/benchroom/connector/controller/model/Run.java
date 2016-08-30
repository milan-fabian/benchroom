package sk.mimac.benchroom.connector.controller.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
public class Run {

    private String runId;
    private List<Long> parameterIds;
    private ZonedDateTime whenStarted;
    private List<RunResult> results;
    private Map<SystemParameter, String> systemParameters;

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public List<Long> getParameterIds() {
        return parameterIds;
    }

    public void setParameterIds(List<Long> parameterIds) {
        this.parameterIds = parameterIds;
    }

    public ZonedDateTime getWhenStarted() {
        return whenStarted;
    }

    public void setWhenStarted(ZonedDateTime whenStarted) {
        this.whenStarted = whenStarted;
    }

    public List<RunResult> getResults() {
        return results;
    }

    public void setResults(List<RunResult> results) {
        this.results = results;
    }

    public Map<SystemParameter, String> getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(Map<SystemParameter, String> systemParameters) {
        this.systemParameters = systemParameters;
    }

    public static class RunResult {

        private long monitorId;
        private double result;

        public long getMonitorId() {
            return monitorId;
        }

        public void setMonitorId(long monitorId) {
            this.monitorId = monitorId;
        }

        public double getResult() {
            return result;
        }

        public void setResult(double result) {
            this.result = result;
        }

    }
}
