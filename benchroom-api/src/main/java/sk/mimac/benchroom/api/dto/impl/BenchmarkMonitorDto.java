package sk.mimac.benchroom.api.dto.impl;

import java.util.Objects;
import org.hibernate.validator.constraints.NotBlank;
import sk.mimac.benchroom.api.dto.Dto;
import sk.mimac.benchroom.api.enums.MonitorType;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkMonitorDto implements Dto {

    private Long id;

    @NotBlank
    private String name;

    private MonitorType type;

    private Long benchmarkSuiteId;

    private String action;

    public BenchmarkMonitorDto() {
    }

    public BenchmarkMonitorDto(Long id) {
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

    public MonitorType getType() {
        return type;
    }

    public void setType(MonitorType type) {
        this.type = type;
    }

    public Long getBenchmarkSuiteId() {
        return benchmarkSuiteId;
    }

    public void setBenchmarkSuiteId(Long benchmarkSuiteId) {
        this.benchmarkSuiteId = benchmarkSuiteId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public int hashCode() {
        return 37 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkMonitorDto other = (BenchmarkMonitorDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkMonitorDto{" + "id=" + id + ", name=" + name + ", type=" + type + ", benchmarkSuiteId=" + benchmarkSuiteId + ", action=" + action + '}';
    }

}
