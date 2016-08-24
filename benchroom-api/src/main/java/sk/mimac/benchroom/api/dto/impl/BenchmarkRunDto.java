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

    private BenchmarkParameterDto benchmarkParameter;

    private ZonedDateTime whenStarted;

    private Map<SystemParameter, String> hardwareParameters;

    private List<BenchmarkRunResultDto> results;

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

    public BenchmarkParameterDto getBenchmarkParameter() {
        return benchmarkParameter;
    }

    public void setBenchmarkParameter(BenchmarkParameterDto benchmarkParameter) {
        this.benchmarkParameter = benchmarkParameter;
    }

    public ZonedDateTime getWhenStarted() {
        return whenStarted;
    }

    public void setWhenStarted(ZonedDateTime whenStarted) {
        this.whenStarted = whenStarted;
    }

    public Map<SystemParameter, String> getHardwareParameters() {
        return hardwareParameters;
    }

    public void setHardwareParameters(Map<SystemParameter, String> hardwareParameters) {
        this.hardwareParameters = hardwareParameters;
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
