package sk.mimac.benchroom.api.dto.impl;

import java.time.LocalDate;
import java.util.Objects;
import org.hibernate.validator.constraints.NotBlank;
import sk.mimac.benchroom.api.dto.Dto;

/**
 *
 * @author Milan Fabian
 */
public class SoftwareVersionDto implements Dto {

    private Long id;

    @NotBlank
    private String name;

    private LocalDate releaseDate;

    public SoftwareVersionDto() {
    }

    public SoftwareVersionDto(Long id) {
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int hashCode() {
        return 29 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SoftwareVersionDto other = (SoftwareVersionDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "SoftwareVersionDto{" + "id=" + id + ", name=" + name + ", releaseDate=" + releaseDate + '}';
    }

}
