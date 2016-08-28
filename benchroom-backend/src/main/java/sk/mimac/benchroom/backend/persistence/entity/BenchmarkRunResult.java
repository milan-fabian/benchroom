package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Milan Fabian
 */
@Entity
@Table(name = "benchmark_run_result")
public class BenchmarkRunResult implements EntityInterface, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "run_id", nullable = false, updatable = false)
    private BenchmarkRun run;

    @ManyToOne
    @JoinColumn(name = "monitor_id", nullable = false, updatable = false)
    private BenchmarkMonitor monitor;

    @Basic(optional = false)
    @Column(name = "result_value", unique = false, nullable = false)
    private double result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BenchmarkRun getRun() {
        return run;
    }

    public void setRun(BenchmarkRun run) {
        this.run = run;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public BenchmarkMonitor getMonitor() {
        return monitor;
    }

    public void setMonitor(BenchmarkMonitor monitor) {
        this.monitor = monitor;
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
        final BenchmarkRunResult other = (BenchmarkRunResult) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.monitor, other.monitor);
    }


    @Override
    public String toString() {
        return "BenchmarkRunResult{" + "id=" + id + ", result=" + result + '}';
    }

}
