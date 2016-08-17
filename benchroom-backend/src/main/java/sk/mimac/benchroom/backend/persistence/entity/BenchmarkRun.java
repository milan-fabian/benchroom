package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
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
    @JoinColumn(name = "benchmark_parameter_id", nullable = false)
    private BenchmarkParameter benchmarkParameter;

    @Basic(optional = false)
    @Column(name = "when_started", unique = false, nullable = false)
    private ZonedDateTime whenStarted;

    @Basic(optional = false)
    @Column(name = "average_run_time", unique = false, nullable = false)
    private int averageRunTime;

    @Basic(optional = false)
    @Column(name = "number_of_runs", unique = false, nullable = false)
    private int numberOfRuns;

    @Lob
    @Basic(optional = true)
    @Column(name = "command_line_output", unique = false, nullable = true)
    private String commandLineOutput;

    @Basic(optional = false)
    @Column(name = "result_code", unique = false, nullable = false)
    private int resultCode;

    @ElementCollection
    @JoinTable(name = "system_parameters", joinColumns = @JoinColumn(name = "benchmark_run"))
    @MapKeyColumn(name = "system_key")
    @Column(name = "system_value")
    private Map<SystemParameter, String> hardwareParameters;

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

    public int getAverageRunTime() {
        return averageRunTime;
    }

    public void setAverageRunTime(int averageRunTime) {
        this.averageRunTime = averageRunTime;
    }

    public int getNumberOfRuns() {
        return numberOfRuns;
    }

    public void setNumberOfRuns(int numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    public SoftwareVersion getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(SoftwareVersion softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public BenchmarkParameter getBenchmarkParameter() {
        return benchmarkParameter;
    }

    public void setBenchmarkParameter(BenchmarkParameter benchmarkParameter) {
        this.benchmarkParameter = benchmarkParameter;
    }

    public Map<SystemParameter, String> getHardwareParameters() {
        return hardwareParameters;
    }

    public void setHardwareParameters(Map<SystemParameter, String> hardwareParameters) {
        this.hardwareParameters = hardwareParameters;
    }

    public String getCommandLineOutput() {
        return commandLineOutput;
    }

    public void setCommandLineOutput(String commandLineOutput) {
        this.commandLineOutput = commandLineOutput;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
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
