package sk.mimac.benchroom.web.model;

import java.util.ArrayList;
import java.util.List;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunResultDto;
import sk.mimac.benchroom.web.tag.RunResultPrinter;

/**
 *
 * @author Milan Fabian
 */
public class XYGraphModel {

    private final double x;
    private final double y;
    private final List<String> title = new ArrayList<>();

    public XYGraphModel(BenchmarkRunDto run, long divisorX, long divisorY) {
        BenchmarkRunResultDto resultX = run.getResults().get(0);
        BenchmarkRunResultDto resultY = run.getResults().get(1);
        x = resultX.getResult() / divisorX;
        y = resultY.getResult() / divisorY;
        StringBuilder builder = new StringBuilder();
        run.getBenchmarkParameters().forEach(param -> builder.append(param.getName()).append(", "));
        builder.deleteCharAt(builder.length() - 2);
        title.add(builder.toString());
        title.add(resultX.getMonitorName() + ": " + RunResultPrinter.printResult(resultX));
        title.add(resultY.getMonitorName() + ": " + RunResultPrinter.printResult(resultY));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public List<String> getTitle() {
        return title;
    }

}
