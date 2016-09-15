package sk.mimac.benchroom.web.tag;

import java.io.IOException;
import java.util.Map;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
public class SystemParameterPrinter extends SimpleTagSupport {

    private Map.Entry<SystemParameter, String> entry;

    public void setEntry(Map.Entry<SystemParameter, String> entry) {
        this.entry = entry;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.println(printParameter(entry));
    }

    private static String printParameter(Map.Entry<SystemParameter, String> entry) {
        String value = entry.getValue();
        switch (entry.getKey()) {
            case CPU_BASE_FREQUENCY:
            case CPU_MAX_FREQUENCY:
                long frequency = Long.parseLong(value);
                if (frequency > 2 * 1000l) {
                    return String.format("%.2f GHz", frequency / (1000.0));
                } else {
                    return value + " MHz";
                }
            case RAM_SIZE:
                long size = Long.parseLong(value);
                if (size > 2 * 1024 * 1024l * 1024) {
                    return String.format("%.2f GB", size / (1024 * 1024.0 * 1024));
                } else if (size > 2 * 1024l * 1024) {
                    return String.format("%.2f MB", size / (1024 * 1024.0));
                } else if (size > 2 * 1024l) {
                    return String.format("%.2f KB", size / (1024.0));
                } else {
                    return value + " B";
                }
            case CPU_MANUFACTURER:
            case CPU_FAMILY:
            case CPU_NAME:
            case CPU_NUM_CORES:
            case CPU_NUM_THREADS:
            case OS_NAME:
            case OS_VERSION:
            case OS_KERNEL_VERSION:
            case SYSTEM_NAME:
            default:
                return value;
        }
    }
}
