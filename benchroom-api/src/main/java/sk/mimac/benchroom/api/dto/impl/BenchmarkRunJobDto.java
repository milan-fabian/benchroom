package sk.mimac.benchroom.api.dto.impl;

import java.util.Objects;
import sk.mimac.benchroom.api.enums.JobStatus;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkRunJobDto {

    private Long id;

    private Long benchmarkSuiteId;

    private Long softwareVersionId;

    private short minPriority;

    private int numberOfRuns;

    private JobStatus status;

    public BenchmarkRunJobDto(Long id) {
        this.id = id;
    }

    public BenchmarkRunJobDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Long getBenchmarkSuiteId() {
        return benchmarkSuiteId;
    }

    public void setBenchmarkSuiteId(Long benchmarkSuiteId) {
        this.benchmarkSuiteId = benchmarkSuiteId;
    }

    public Long getSoftwareVersionId() {
        return softwareVersionId;
    }

    public void setSoftwareVersionId(Long softwareVersionId) {
        this.softwareVersionId = softwareVersionId;
    }

    public short getMinPriority() {
        return minPriority;
    }

    public void setMinPriority(short minPriority) {
        this.minPriority = minPriority;
    }

    public int getNumberOfRuns() {
        return numberOfRuns;
    }

    public void setNumberOfRuns(int numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    @Override
    public int hashCode() {
        return 47 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkRunJobDto other = (BenchmarkRunJobDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkRunJobDto{" + "id=" + id + ", benchmarkSuiteId=" + benchmarkSuiteId + ", softwareVersionId=" + softwareVersionId + ", minPriority=" + minPriority + ", numberOfRuns=" + numberOfRuns + ", status=" + status + '}';
    }

}
