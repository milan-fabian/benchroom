package sk.mimac.benchroom.api.filter;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkParameterFilter extends AbstractFilter {

    private Long suiteId;

    public Long getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Long suiteId) {
        this.suiteId = suiteId;
    }

}
