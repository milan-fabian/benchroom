package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Milan Fabian
 */
@Entity
@Table(name = "software_version", indexes = {
    @Index(name = "software_version_sofware_name", columnList = "name, software_id")
})
public class SoftwareVersion implements EntityInterface, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Basic(optional = true)
    @Column(name = "release_date", unique = false, nullable = true)
    private LocalDate releaseDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "software_id", nullable = false, updatable = false)
    private Software software;

    public SoftwareVersion() {
    }

    public SoftwareVersion(Long id) {
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    @Override
    public int hashCode() {
        return 31 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SoftwareVersion other = (SoftwareVersion) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "SoftwareVersion{" + "id=" + id + ", name=" + name + '}';
    }

}
