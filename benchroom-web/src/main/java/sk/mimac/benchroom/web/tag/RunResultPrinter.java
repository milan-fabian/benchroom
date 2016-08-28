package sk.mimac.benchroom.web.tag;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunResultDto;

/**
 *
 * @author Milan Fabian
 */
public class RunResultPrinter extends SimpleTagSupport {

    private BenchmarkRunResultDto runResult;

    public void setRunResult(BenchmarkRunResultDto runResult) {
        this.runResult = runResult;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.println(printResult(runResult));
    }

    public String printResult(BenchmarkRunResultDto runResult) {
        double value = runResult.getResult();
        switch (runResult.getMonitorType()) {
            case RUN_TIME:
                if (value < 2 * 1000) {
                    return String.format("%d ms", (long) value);
                } else {
                    return String.format("%f s", (value / 1000));
                }
            case FILE_SIZE:
                if (value < 2l * 1024) {
                    return String.format("%d B", (long) value);
                } else if (value < 2l * 1024 * 1024) {
                    return String.format("%f KB", ((long) value) / 1024d);
                } else if (value < 2l * 1024 * 1024 * 1024) {
                    return String.format("%f MB", ((long) value) / 1024d / 1024);
                }
        }
        return Double.toString(value);
    }

}