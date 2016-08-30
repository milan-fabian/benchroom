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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Milan Fabian
 */
@Entity
@NamedQueries({
    @NamedQuery(name = BenchmarkParameter.GET_BY_SUITE_POSITION_PRIORITY, query = "SELECT p FROM BenchmarkParameter p "
            + "WHERE p.benchmarkSuite.id = :suiteId AND p.position = :position AND p.priority >= :minPriority")
})
@Table(name = "benchmark_parameter", indexes = {
    @Index(name = "benchmark_parameter_suite_name", columnList = "benchmark_suite_id, name")
})
public class BenchmarkParameter implements EntityInterface, Serializable {

    public static final String GET_BY_SUITE_POSITION_PRIORITY = "BenchmarkParameter.get_by_suite_position_priority";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "benchmark_suite_id", nullable = false, updatable = false)
    private BenchmarkSuite benchmarkSuite;

    @Basic(optional = true)
    @Column(name = "name", unique = false, nullable = true)
    private String name;

    @Basic(optional = false)
    @Column(name = "position", unique = false, nullable = false)
    private short position;

    @Basic(optional = false)
    @Column(name = "priority", unique = false, nullable = false)
    private short priority;

    @Lob
    @Basic(optional = true)
    @Column(name = "command_line_arguments", unique = false, nullable = true)
    private String commandLineArguments;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getPosition() {
        return position;
    }

    public void setPosition(short position) {
        this.position = position;
    }

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
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
        return "BenchmarkParameter{" + "id=" + id + ", name=" + name + ", position=" + position + '}';
    }
    
}
