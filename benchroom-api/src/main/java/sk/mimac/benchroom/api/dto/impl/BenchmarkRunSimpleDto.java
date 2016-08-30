package sk.mimac.benchroom.api.dto.impl;

import java.time.ZonedDateTime;
import java.util.Objects;
import sk.mimac.benchroom.api.dto.Dto;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkRunSimpleDto implements Dto {

    private Long id;

    private String benchmarkParameters;

    private ZonedDateTime whenStarted;

    private String systemParameters;

    private String results;

    public BenchmarkRunSimpleDto(Long id) {
        this.id = id;
    }

    public BenchmarkRunSimpleDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenchmarkParameters() {
        return benchmarkParameters;
    }

    public void setBenchmarkParameters(String benchmarkParameters) {
        this.benchmarkParameters = benchmarkParameters;
    }

    public ZonedDateTime getWhenStarted() {
        return whenStarted;
    }

    public void setWhenStarted(ZonedDateTime whenStarted) {
        this.whenStarted = whenStarted;
    }

    public String getSystemParameters() {
        return systemParameters;
    }

    public void setSystemParameters(String systemParameters) {
        this.systemParameters = systemParameters;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    @Override
    public int hashCode() {
        return 43 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkRunSimpleDto other = (BenchmarkRunSimpleDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkRunDto{" + "id=" + id + ", whenStarted=" + whenStarted + '}';
    }

}
