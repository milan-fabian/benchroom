package sk.mimac.benchroom.api.service;

import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkParameterDto;
import sk.mimac.benchroom.api.filter.BenchmarkParameterFilter;

/**
 *
 * @author Milan Fabian
 */
public interface BenchmarkParameterService {

    BenchmarkParameterDto getParameterById(long id);

    long insertParameter(BenchmarkParameterDto parameter);

    void updateParameter(BenchmarkParameterDto parameter);

    Page<BenchmarkParameterDto> getParameterPage(BenchmarkParameterFilter filter);
}
