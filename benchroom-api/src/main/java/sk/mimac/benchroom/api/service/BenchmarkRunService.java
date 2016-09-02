package sk.mimac.benchroom.api.service;

import java.util.List;
import java.util.Map;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunSimpleDto;
import sk.mimac.benchroom.api.filter.BenchmarkRunFilter;

/**
 *
 * @author Milan Fabian
 */
public interface BenchmarkRunService {

    BenchmarkRunDto getRunById(long id);

    void insertRun(BenchmarkRunDto dto, Map<Long, Double> monitorResults);

    Page<BenchmarkRunSimpleDto> getRunPageSimple(BenchmarkRunFilter filter);

    Page<BenchmarkRunDto> getRunPage(BenchmarkRunFilter filter);
    
    List<BenchmarkRunDto> getRunsByIds(List<Long> ids);
}
