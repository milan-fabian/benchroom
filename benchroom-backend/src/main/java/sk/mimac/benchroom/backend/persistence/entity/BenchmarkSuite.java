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
@Table(name = "benchmark_suite", indexes = {
    @Index(name = "benchmark_suite_sofware_name", columnList = "software_id, name")
})
public class BenchmarkSuite implements EntityInterface, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "software_id", nullable = false, updatable = false)
    private Software software;

    @Lob
    @Basic(optional = true)
    @Column(name = "setup_script", unique = false, nullable = true)
    private String setupScript;

    @Lob
    @Basic(optional = true)
    @Column(name = "cleanup_script", unique = false, nullable = true)
    private String cleanupScript;

    public BenchmarkSuite() {
    }

    public BenchmarkSuite(Long id) {
        this.id = id;
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

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public String getSetupScript() {
        return setupScript;
    }

    public void setSetupScript(String setupScript) {
        this.setupScript = setupScript;
    }

    public String getCleanupScript() {
        return cleanupScript;
    }

    public void setCleanupScript(String cleanupScript) {
        this.cleanupScript = cleanupScript;
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
        final BenchmarkSuite other = (BenchmarkSuite) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkSuite{" + "id=" + id + ", name=" + name + '}';
    }

}
