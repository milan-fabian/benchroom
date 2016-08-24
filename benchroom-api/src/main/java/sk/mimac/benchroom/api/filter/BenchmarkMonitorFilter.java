package sk.mimac.benchroom.api.filter;

import sk.mimac.benchroom.api.dto.impl.BenchmarkMonitorDto;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkMonitorFilter extends AbstractFilter<BenchmarkMonitorDto> {

    private Long suiteId;

    public Long getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Long suiteId) {
        this.suiteId = suiteId;
    }

}
