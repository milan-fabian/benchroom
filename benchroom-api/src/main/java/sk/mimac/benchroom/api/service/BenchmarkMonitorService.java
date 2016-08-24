package sk.mimac.benchroom.api.service;

import java.util.List;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkMonitorDto;
import sk.mimac.benchroom.api.filter.BenchmarkMonitorFilter;

/**
 *
 * @author Milan Fabian
 */
public interface BenchmarkMonitorService {

    BenchmarkMonitorDto getMonitorById(long id);

    long insertMonitor(BenchmarkMonitorDto monitor);

    void updateMonitor(BenchmarkMonitorDto monitor);

    Page<BenchmarkMonitorDto> getMonitorPage(BenchmarkMonitorFilter filter);

    List<BenchmarkMonitorDto> getMonitorsForSuite(long suiteId);

}
