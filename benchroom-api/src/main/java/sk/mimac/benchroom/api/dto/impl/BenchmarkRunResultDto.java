package sk.mimac.benchroom.api.dto.impl;

import java.util.Objects;
import sk.mimac.benchroom.api.dto.Dto;
import sk.mimac.benchroom.api.enums.MonitorType;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkRunResultDto implements Dto {

    private Long id;

    private String monitorName;

    private MonitorType monitorType;

    private double result;

    public BenchmarkRunResultDto() {
    }

    public BenchmarkRunResultDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public MonitorType getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(MonitorType monitorType) {
        this.monitorType = monitorType;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        return 59 + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BenchmarkRunResultDto other = (BenchmarkRunResultDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "BenchmarkRunResultDto{" + "id=" + id + ", monitorName=" + monitorName + ", monitorType=" + monitorType + ", result=" + result + '}';
    }

}
