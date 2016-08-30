package sk.mimac.benchroom.api.dto.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import sk.mimac.benchroom.api.dto.Dto;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkRunDto implements Dto {

    private Long id;

    private SoftwareVersionDto softwareVersion;

    private List<BenchmarkParameterDto> benchmarkParameters;

    private ZonedDateTime whenStarted;

    private Map<SystemParameter, String> systemParameters;

    private List<BenchmarkRunResultDto> results;

    private Long benchmarkSuiteId;

    public BenchmarkRunDto(Long id) {
        this.id = id;
    }

    public BenchmarkRunDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SoftwareVersionDto getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(SoftwareVersionDto softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public List<BenchmarkParameterDto> getBenchmarkParameters() {
        return benchmarkParameters;
    }

    public void setBenchmarkParameters(List<BenchmarkParameterDto> benchmarkParameters) {
        this.benchmarkParameters = benchmarkParameters;
    }

    public Long getBenchmarkSuiteId() {
        return benchmarkSuiteId;
    }

    public void setBenchmarkSuiteId(Long benchmarkSuiteId) {
        this.benchmarkSuiteId = benchmarkSuiteId;
    }

    public ZonedDateTime getWhenStarted() {
        return whenStarted;
    }

    public void setWhenStarted(ZonedDateTime whenStarted) {
        this.whenStarted = whenStarted;
    }

    public Map<SystemParameter, String> getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(Map<SystemParameter, String> systemParameters) {
        this.systemParameters = systemParameters;
    }

    public List<BenchmarkRunResultDto> getResults() {
        return results;
    }

    public void setResults(List<BenchmarkRunResultDto> results) {
        this.results = results;
    }

    @Override
    public int hashCode() {
        return 43 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkRunDto other = (BenchmarkRunDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkRunDto{" + "id=" + id + ", whenStarted=" + whenStarted + '}';
    }

}
