package sk.mimac.benchroom.api.dto.impl;

import java.util.Map;
import java.util.Objects;
import sk.mimac.benchroom.api.dto.Dto;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
public class SystemInfoDto implements Dto {

    private Long id;

    private Map<SystemParameter, String> parameters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SystemInfoDto() {
    }

    public SystemInfoDto(Long id) {
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
        return 71 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystemInfoDto other = (SystemInfoDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "SystemDto{" + "id=" + id + '}';
    }

}
