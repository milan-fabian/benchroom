package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
@Entity
@Table(name = "benchmark_run")
public class BenchmarkRun implements EntityInterface, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "software_version_id", nullable = false)
    private SoftwareVersion softwareVersion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "benchmark_suite_id", nullable = false)
    private BenchmarkSuite benchmarkSuite;

    @ManyToMany()
    @JoinTable(name = "benchmark_run_parameter",
            joinColumns = {
                @JoinColumn(name = "benchmark_run_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "benchmark_parameter_id", nullable = false, updatable = false)})
    private Set<BenchmarkParameter> benchmarkParameters;

    @Basic(optional = false)
    @Column(name = "when_started", unique = false, nullable = false)
    private ZonedDateTime whenStarted;

    @ElementCollection
    @JoinTable(name = "system_parameters", joinColumns = @JoinColumn(name = "benchmark_run"))
    @MapKeyColumn(name = "system_key")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "system_value")
    private Map<SystemParameter, String> systemParameters;

    @OneToMany(mappedBy = "run", cascade = {CascadeType.ALL})
    private Set<BenchmarkRunResult> results;

    public BenchmarkRun() {
    }

    public BenchmarkRun(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getWhenStarted() {
        return whenStarted;
    }

    public void setWhenStarted(ZonedDateTime whenStarted) {
        this.whenStarted = whenStarted;
    }

    public SoftwareVersion getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(SoftwareVersion softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public Set<BenchmarkParameter> getBenchmarkParameters() {
        return benchmarkParameters;
    }

    public void setBenchmarkParameters(Set<BenchmarkParameter> benchmarkParameters) {
        this.benchmarkParameters = benchmarkParameters;
    }

    public Map<SystemParameter, String> getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(Map<SystemParameter, String> systemParameters) {
        this.systemParameters = systemParameters;
    }

    public Set<BenchmarkRunResult> getResults() {
        return results;
    }

    public void setResults(Set<BenchmarkRunResult> results) {
        this.results = results;
    }

    public BenchmarkSuite getBenchmarkSuite() {
        return benchmarkSuite;
    }

    public void setBenchmarkSuite(BenchmarkSuite benchmarkSuite) {
        this.benchmarkSuite = benchmarkSuite;
    }

    @Override
    public int hashCode() {
        return 37 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkRun other = (BenchmarkRun) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkRun{" + "id=" + id + ", whenStarted=" + whenStarted + '}';
    }

}
