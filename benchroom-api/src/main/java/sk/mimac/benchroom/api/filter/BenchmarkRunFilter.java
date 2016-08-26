package sk.mimac.benchroom.api.filter;

import sk.mimac.benchroom.api.dto.impl.BenchmarkRunSimpleDto;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkRunFilter extends AbstractFilter<BenchmarkRunSimpleDto> {

    private Long softwareVersionId;
    private Long softwareId;
    private Long benchmarkSuiteId;
    private Long benchmarkParameterId;

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

}
