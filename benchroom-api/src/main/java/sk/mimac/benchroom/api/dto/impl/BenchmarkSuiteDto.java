package sk.mimac.benchroom.api.dto.impl;

import java.util.Objects;
import org.hibernate.validator.constraints.NotBlank;
import sk.mimac.benchroom.api.dto.Dto;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkSuiteDto implements Dto {

    private Long id;

    @NotBlank
    private String name;

    private Long softwareId;

    private String softwareName;

    public BenchmarkSuiteDto() {
    }

    public BenchmarkSuiteDto(Long id) {
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

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    @Override
    public int hashCode() {
        return 67 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkSuiteDto other = (BenchmarkSuiteDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkSuiteDto{" + "id=" + id + ", name=" + name + ", softwareId=" + softwareId + '}';
    }

}