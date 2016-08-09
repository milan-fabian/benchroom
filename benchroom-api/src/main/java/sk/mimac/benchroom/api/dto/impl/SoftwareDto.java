package sk.mimac.benchroom.api.dto.impl;

import java.io.Serializable;
import java.util.Objects;
import sk.mimac.benchroom.api.dto.Dto;

/**
 *
 * @author Milan Fabian
 */
public class SoftwareDto implements Dto, Serializable {

    private Long id;

    private String name;

    public SoftwareDto() {
    }

    public SoftwareDto(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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
        final SoftwareDto other = (SoftwareDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "SoftwareDto{" + "id=" + id + ", name=" + name + '}';
    }

}
