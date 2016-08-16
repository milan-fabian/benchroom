package sk.mimac.benchroom.api.dto.impl;

import java.util.Objects;
import org.hibernate.validator.constraints.NotBlank;
import sk.mimac.benchroom.api.dto.Dto;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkParameterDto implements Dto {

    private Long id;

    @NotBlank
    private String name;

    private String commandLineArguments;

    private String commandLineInput;

    private Long benchmarkSuiteId;

    public BenchmarkParameterDto(Long id) {
        this.id = id;
    }

    public BenchmarkParameterDto() {
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

    public String getCommandLineArguments() {
        return commandLineArguments;
    }

    public void setCommandLineArguments(String commandLineArguments) {
        this.commandLineArguments = commandLineArguments;
    }

    public String getCommandLineInput() {
        return commandLineInput;
    }

    public void setCommandLineInput(String commandLineInput) {
        this.commandLineInput = commandLineInput;
    }

    public Long getBenchmarkSuiteId() {
        return benchmarkSuiteId;
    }

    public void setBenchmarkSuiteId(Long benchmarkSuiteId) {
        this.benchmarkSuiteId = benchmarkSuiteId;
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
        final BenchmarkParameterDto other = (BenchmarkParameterDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkParameterDto{" + "id=" + id + ", name=" + name + ", benchmarkSuiteId=" + benchmarkSuiteId + '}';
    }

}
