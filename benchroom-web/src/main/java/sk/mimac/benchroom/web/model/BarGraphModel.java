package sk.mimac.benchroom.web.model;

import java.util.ArrayList;
import java.util.List;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunResultDto;

/**
 *
 * @author Milan Fabian
 */
public class BarGraphModel {

    private final List<Double> values = new ArrayList<>();
    private final List<List<String>> labels = new ArrayList<>();

    public void add(BenchmarkRunDto run, long divisor) {
        BenchmarkRunResultDto result = run.getResults().get(0);
        values.add(Math.round(100 * result.getResult() / divisor) / 100d);
        List<String> temp = new ArrayList();
        run.getBenchmarkParameters().forEach(param -> temp.add(param.getName()));
        labels.add(temp);
    }

    public List<Double> getValues() {
        return values;
    }

    public List<List<String>> getLabels() {
        return labels;
    }

}
