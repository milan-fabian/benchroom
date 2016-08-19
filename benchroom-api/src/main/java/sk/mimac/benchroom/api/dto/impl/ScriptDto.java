package sk.mimac.benchroom.api.dto.impl;

import java.util.Objects;
import java.util.Set;
import sk.mimac.benchroom.api.dto.Dto;
import sk.mimac.benchroom.api.enums.Platform;
import sk.mimac.benchroom.api.enums.ScriptType;

/**
 *
 * @author Milan Fabian
 */
public class ScriptDto implements Dto {

    private Long id;

    private ScriptType type;

    private String scriptData;

    private Long softwareVersionId;

    private Set<Platform> supportedPlatforms;

    public ScriptDto() {
    }

    public ScriptDto(Long id) {
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

    public String getScriptData() {
        return scriptData;
    }

    public void setScriptData(String scriptData) {
        this.scriptData = scriptData;
    }

    public Long getSoftwareVersionId() {
        return softwareVersionId;
    }

    public void setSoftwareVersionId(Long softwareVersionId) {
        this.softwareVersionId = softwareVersionId;
    }

    public Set<Platform> getSupportedPlatforms() {
        return supportedPlatforms;
    }

    public void setSupportedPlatforms(Set<Platform> supportedPlatforms) {
        this.supportedPlatforms = supportedPlatforms;
    }

    @Override
    public int hashCode() {
        return 83 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ScriptDto other = (ScriptDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ScriptDto{" + "id=" + id + ", type=" + type + '}';
    }
}
