package sk.mimac.benchroom.api.system;

/**
 *
 * @author Milan Fabian
 */
public enum SystemParameter {

    /* CPU */
    CPU_MANUFACTURER(SystemParameter.Type.STRING),
    CPU_FAMILY(SystemParameter.Type.STRING),
    CPU_NAME(SystemParameter.Type.STRING),
    CPU_NUM_CORES(SystemParameter.Type.INTEGER),
    CPU_NUM_THREADS(SystemParameter.Type.INTEGER),
    CPU_BASE_FREQUENCY(SystemParameter.Type.DOUBLE),
    CPU_MAX_FREQUENCY(SystemParameter.Type.DOUBLE),
    /* RAM */
    RAM_SIZE(SystemParameter.Type.DOUBLE),
    /* OS */
    OS_NAME(SystemParameter.Type.STRING),
    OS_VERSION(SystemParameter.Type.STRING),
    OS_KERNEL_VERSION(SystemParameter.Type.STRING);

    private final Type type;

    private SystemParameter(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public static enum Type {

        STRING, INTEGER, DOUBLE;
    }
}
