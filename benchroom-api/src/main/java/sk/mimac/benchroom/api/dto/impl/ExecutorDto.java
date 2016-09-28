package sk.mimac.benchroom.api.dto.impl;

import java.util.Map;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
public class ExecutorDto {

    private long id;
    private Map<SystemParameter, String> systemParameters;

    public ExecutorDto() {
    }

    public ExecutorDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<SystemParameter, String> getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(Map<SystemParameter, String> systemParameters) {
        this.systemParameters = systemParameters;
    }

    @Override
    public int hashCode() {
        return 13 + (int) (this.id ^ (this.id >>> 32));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExecutorDto other = (ExecutorDto) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "ExecutorDto{" + "id=" + id + '}';
    }

}
