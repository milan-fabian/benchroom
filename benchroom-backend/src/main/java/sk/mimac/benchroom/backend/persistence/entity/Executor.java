package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Milan Fabian
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Executor.GET_BY_SYSTEM_INFO, query = "SELECT e FROM Executor e WHERE e.systemInfo.id = :systemInfoId")
})
@Table(name = "executor")
public class Executor implements EntityInterface, Serializable {

    public static final String GET_BY_SYSTEM_INFO = "Executor.get_by_system_info";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "system_info", nullable = false, updatable = false)
    private SystemInfo systemInfo;

    @OneToMany(mappedBy = "executor", cascade = CascadeType.ALL)
    private List<BenchmarkRunJob> runJobs;

    public Executor() {
    }

    public Executor(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    public List<BenchmarkRunJob> getRunJobs() {
        return runJobs;
    }

    public void setRunJobs(List<BenchmarkRunJob> runJobs) {
        this.runJobs = runJobs;
    }

    @Override
    public int hashCode() {
        return 41 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Executor other = (Executor) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Executor{" + "id=" + id + '}';
    }

}
