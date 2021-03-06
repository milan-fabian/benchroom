package sk.mimac.benchroom.api.dto.impl;

import java.util.Objects;
import org.hibernate.validator.constraints.NotBlank;
import sk.mimac.benchroom.api.dto.Dto;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkParameterDto implements Dto, Comparable<BenchmarkParameterDto>{

    private Long id;

    @NotBlank
    private String name;

    private String commandLineArguments;

    private String setupScript;

    private String cleanupScript;

    private Long benchmarkSuiteId;

    private short position;

    private short priority;

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

    public short getPosition() {
        return position;
    }

    public void setPosition(short position) {
        this.position = position;
    }

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public Long getBenchmarkSuiteId() {
        return benchmarkSuiteId;
    }

    public void setBenchmarkSuiteId(Long benchmarkSuiteId) {
        this.benchmarkSuiteId = benchmarkSuiteId;
    }

    public String getCleanupScript() {
        return cleanupScript;
    }

    public void setCleanupScript(String cleanupScript) {
        this.cleanupScript = cleanupScript;
    }

    public String getSetupScript() {
        return setupScript;
    }

    public void setSetupScript(String setupScript) {
        this.setupScript = setupScript;
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

    @Override
    public int compareTo(BenchmarkParameterDto o) {
        int result = Short.compare(this.position, o.position);
        if (result == 0) {
            result = this.name.compareTo(o.name);
        }
        return result;
    }

}
