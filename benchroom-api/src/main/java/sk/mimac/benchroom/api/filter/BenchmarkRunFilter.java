package sk.mimac.benchroom.api.filter;

import java.util.Map;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkRunFilter extends AbstractFilter {

    private Long softwareVersionId;
    private Long softwareId;
    private Long benchmarkSuiteId;
    private Long benchmarkParameterId;
    private Map<SystemParameter, String> parameters;

    public Long getSoftwareVersionId() {
        return softwareVersionId;
    }

    public void setSoftwareVersionId(Long softwareVersionId) {
        this.softwareVersionId = softwareVersionId;
    }

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public Long getBenchmarkSuiteId() {
        return benchmarkSuiteId;
    }

    public void setBenchmarkSuiteId(Long benchmarkSuiteId) {
        this.benchmarkSuiteId = benchmarkSuiteId;
    }

    public Long getBenchmarkParameterId() {
        return benchmarkParameterId;
    }

    public void setBenchmarkParameterId(Long benchmarkParameterId) {
        this.benchmarkParameterId = benchmarkParameterId;
    }

    public Map<SystemParameter, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<SystemParameter, String> parameters) {
        this.parameters = parameters;
    }

}
