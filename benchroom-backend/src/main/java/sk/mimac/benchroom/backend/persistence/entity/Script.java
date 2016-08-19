package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import sk.mimac.benchroom.api.enums.Platform;
import sk.mimac.benchroom.api.enums.ScriptType;

/**
 *
 * @author Milan Fabian
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Script.GET_BY_VERSION_PLATFORM_TYPE, query = "SELECT s.script FROM Script s "
            + "WHERE s.type = :type AND :platform MEMBER OF s.supportedPlatforms AND s.softwareVersion.id = :versionId")
})
@Table(name = "script")
public class Script implements Serializable, EntityInterface {

    public static final String GET_BY_VERSION_PLATFORM_TYPE = "Script.get_by_version_platform_type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = false, nullable = false, updatable = false)
    private ScriptType type;

    @Lob
    @Basic(optional = false)
    @Column(name = "script", unique = false, nullable = false)
    private String script;

    @ManyToOne(optional = false)
    @JoinColumn(name = "software_version", nullable = false, updatable = false)
    private SoftwareVersion softwareVersion;

    @ElementCollection(targetClass = Platform.class, fetch = FetchType.EAGER)
    @JoinTable(name = "script_supported_platforms", joinColumns = @JoinColumn(name = "script"))
    @Column(name = "platform", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Platform> supportedPlatforms;

    public Script() {
    }

    public Script(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScriptType getType() {
        return type;
    }

    public void setType(ScriptType type) {
        this.type = type;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public SoftwareVersion getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(SoftwareVersion softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public Set<Platform> getSupportedPlatforms() {
        return supportedPlatforms;
    }

    public void setSupportedPlatforms(Set<Platform> supportedPlatforms) {
        this.supportedPlatforms = supportedPlatforms;
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
        final Script other = (Script) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Script{" + "id=" + id + ", type=" + type + '}';
    }

}
