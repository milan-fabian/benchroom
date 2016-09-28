package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import sk.mimac.benchroom.api.enums.JobStatus;

/**
 *
 * @author Milan Fabian
 */
@Entity
@Table(name = "benchmark_run_job")
public class BenchmarkRunJob implements Serializable, EntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "software_version_id", nullable = false, updatable = false)
    private SoftwareVersion softwareVersion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "benchmark_suite_id", nullable = false, updatable = false)
    private BenchmarkSuite benchmarkSuite;

    @Basic(optional = false)
    @Column(name = "min_priority", unique = false, nullable = false)
    private short minPriority;

    @Basic(optional = false)
    @Column(name = "number_of_runs", unique = false, nullable = false)
    private int numberOfRuns;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", unique = false, nullable = false, updatable = false)
    private JobStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "executor", nullable = false, updatable = false)
    private Executor executor;

    public BenchmarkRunJob() {
    }

    public BenchmarkRunJob(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public SoftwareVersion getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(SoftwareVersion softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public BenchmarkSuite getBenchmarkSuite() {
        return benchmarkSuite;
    }

    public void setBenchmarkSuite(BenchmarkSuite benchmarkSuite) {
        this.benchmarkSuite = benchmarkSuite;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public int hashCode() {
        return 97 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkRunJob other = (BenchmarkRunJob) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkRunJob{" + "id=" + id + ", minPriority=" + minPriority + ", numberOfRuns=" + numberOfRuns + '}';
    }

}
