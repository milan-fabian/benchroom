package sk.mimac.benchroom.api.filter;

import sk.mimac.benchroom.api.dto.impl.BenchmarkParameterDto;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkParameterFilter extends AbstractFilter<BenchmarkParameterDto> {

    private Long suiteId;

    public Long getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Long suiteId) {
        this.suiteId = suiteId;
    }

}
