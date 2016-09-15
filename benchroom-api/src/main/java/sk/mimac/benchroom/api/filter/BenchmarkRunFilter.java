package sk.mimac.benchroom.api.filter;

import java.util.List;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkRunFilter extends AbstractFilter {

    private Long softwareVersionId;
    private Long softwareId;
    private Long benchmarkSuiteId;
    private List<Long> benchmarkParameterIds;
    private Long systemInfoId;

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

    public List<Long> getBenchmarkParameterIds() {
        return benchmarkParameterIds;
    }

    public void setBenchmarkParameterIds(List<Long> benchmarkParameterIds) {
        this.benchmarkParameterIds = benchmarkParameterIds;
    }

    public Long getSystemInfoId() {
        return systemInfoId;
    }

    public void setSystemInfoId(Long systemInfoId) {
        this.systemInfoId = systemInfoId;
    }

}
