package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Milan Fabian
 */
@Entity
@Table(name = "benchmark_parameter", indexes = {
    @Index(name = "benchmark_parameter_suite_name", columnList = "benchmark_suite_id, name")
})
public class BenchmarkParameter implements EntityInterface, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "benchmark_suite_id", nullable = false, updatable = false)
    private BenchmarkSuite benchmarkSuite;

    @Basic(optional = true)
    @Column(name = "name", unique = false, nullable = true)
    private String name;

    @Lob
    @Basic(optional = true)
    @Column(name = "command_line_arguments", unique = false, nullable = true)
    private String commandLineArguments;

    @Lob
    @Basic(optional = true)
    @Column(name = "command_line_input", unique = false, nullable = true)
    private String commandLineInput;

    public BenchmarkParameter() {
    }

    public BenchmarkParameter(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommandLineArguments() {
        return commandLineArguments;
    }

    public void setCommandLineArguments(String commandLineArguments) {
        this.commandLineArguments = commandLineArguments;
    }

    public BenchmarkSuite getBenchmarkSuite() {
        return benchmarkSuite;
    }

    public void setBenchmarkSuite(BenchmarkSuite benchmarkSuite) {
        this.benchmarkSuite = benchmarkSuite;
    }

    public String getCommandLineInput() {
        return commandLineInput;
    }

    public void setCommandLineInput(String commandLineInput) {
        this.commandLineInput = commandLineInput;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return 7 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkParameter other = (BenchmarkParameter) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkParameter{" + "id=" + id + ", commandLineArguments=" + commandLineArguments + '}';
    }

}
