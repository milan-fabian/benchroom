package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import sk.mimac.benchroom.api.enums.MonitorType;

/**
 *
 * @author Milan Fabian
 */
@Entity
@Table(name = "benchmark_monitor")
public class BenchmarkMonitor implements EntityInterface, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = false, nullable = false, updatable = false)
    private MonitorType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "benchmark_suite_id", nullable = false, updatable = false)
    private BenchmarkSuite benchmarkSuite;

    @Basic(optional = true)
    @Column(name = "name", unique = false, nullable = true)
    private String action;

    public BenchmarkMonitor(Long id) {
        this.id = id;
    }

    public BenchmarkMonitor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BenchmarkSuite getBenchmarkSuite() {
        return benchmarkSuite;
    }

    public void setBenchmarkSuite(BenchmarkSuite benchmarkSuite) {
        this.benchmarkSuite = benchmarkSuite;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public MonitorType getType() {
        return type;
    }

    public void setType(MonitorType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return 11 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkMonitor other = (BenchmarkMonitor) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkMonitor{" + "id=" + id + ", name=" + name + ", type=" + type + ", benchmarkSuite=" + benchmarkSuite + ", action=" + action + '}';
    }

}
