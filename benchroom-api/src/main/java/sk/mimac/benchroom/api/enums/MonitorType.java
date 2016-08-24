package sk.mimac.benchroom.api.enums;

/**
 *
 * @author Milan Fabian
 */
public enum MonitorType {

    RUN_TIME("second"),
    FILE_SIZE("bytes");

    private final String unit;

    private MonitorType(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

}
