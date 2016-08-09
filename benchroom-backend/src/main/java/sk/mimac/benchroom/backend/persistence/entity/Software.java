package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Milan Fabian
 */
@Entity
@Table(name = "software")
public class Software implements EntityInterface, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "software")
    private Set<SoftwareVersion> versions;

    public Software() {
    }

    public Software(Long id) {
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

    @Override
    public int hashCode() {
        return 59 + Objects.hashCode(this.id);
    }

    public Set<SoftwareVersion> getVersions() {
        return versions;
    }

    public void setVersions(Set<SoftwareVersion> versions) {
        this.versions = versions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Software other = (Software) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Software{" + "id=" + id + ", name=" + name + '}';
    }
}
