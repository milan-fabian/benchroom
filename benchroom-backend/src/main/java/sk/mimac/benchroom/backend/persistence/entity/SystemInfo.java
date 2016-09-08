package sk.mimac.benchroom.backend.persistence.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
@Entity
@Table(name = "system_info")
public class SystemInfo implements EntityInterface, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @JoinTable(name = "system_parameters", joinColumns = @JoinColumn(name = "system_id"))
    @MapKeyColumn(name = "system_key")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "system_value")
    private Map<SystemParameter, String> parameters;

    public SystemInfo() {
    }

    public SystemInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<SystemParameter, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<SystemParameter, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public int hashCode() {
        return 59 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystemInfo other = (SystemInfo) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "System{" + "id=" + id + '}';
    }

}
