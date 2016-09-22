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

    private final String label;
    private final List<GraphPoint> data = new ArrayList<>();

    public XYGraphModel(String label) {
        this.label = label;
    }

    public void addPoint(BenchmarkRunDto run, long divisorX, long divisorY) {
        BenchmarkRunResultDto resultX = run.getResults().get(0);
        BenchmarkRunResultDto resultY = run.getResults().get(1);
        double x = resultX.getResult() / divisorX;
        double y = resultY.getResult() / divisorY;
        StringBuilder builder = new StringBuilder();
        run.getBenchmarkParameters().forEach(param -> builder.append(param.getName()).append(", "));
        builder.deleteCharAt(builder.length() - 2);
        List<String> title = new ArrayList<>();
        title.add(builder.toString());
        title.add(resultX.getMonitorName() + ": " + RunResultPrinter.printResult(resultX));
        title.add(resultY.getMonitorName() + ": " + RunResultPrinter.printResult(resultY));
        data.add(new GraphPoint(x, y, title));
    }

    public String getLabel() {
        return label;
    }

    public List<GraphPoint> getData() {
        return data;
    }

    public class GraphPoint {

        private final double x;
        private final double y;
        private final List<String> title;

        public GraphPoint(double x, double y, List<String> title) {
            this.x = x;
            this.y = y;
            this.title = title;
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

}
