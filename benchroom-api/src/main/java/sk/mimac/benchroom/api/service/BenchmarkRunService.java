package sk.mimac.benchroom.api.service;

import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.api.filter.BenchmarkRunFilter;

/**
 *
 * @author Milan Fabian
 */
public interface BenchmarkRunService {

    BenchmarkRunDto getRunById(long id);

    Page<BenchmarkRunDto> getRunPage(BenchmarkRunFilter filter);

}
