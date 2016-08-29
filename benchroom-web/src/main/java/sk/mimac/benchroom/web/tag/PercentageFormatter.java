package sk.mimac.benchroom.web.tag;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Milan Fabian
 */
public class PercentageFormatter extends SimpleTagSupport {

    private double percentage;

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        percentage = (percentage - 1) * 100;
        if (percentage < 0) {
            out.println(String.format("<span style='color:darkgreen'>%.1f %%</span>", percentage));
        } else if (percentage == 0) {
            out.println("0 %");
        } else {
            out.println(String.format("<span style='color:darkred'>%.1f %%</span>", percentage));
        }
    }
}
