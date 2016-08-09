package sk.mimac.benchroom.api.filter;

import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkSuiteFilter extends AbstractFilter<BenchmarkSuiteDto> {

    private Long softwareId;

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

}
