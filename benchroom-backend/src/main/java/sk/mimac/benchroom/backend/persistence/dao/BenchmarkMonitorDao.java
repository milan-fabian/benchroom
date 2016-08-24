package sk.mimac.benchroom.backend.persistence.dao;

import java.util.List;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkMonitor;

/**
 *
 * @author Milan Fabian
 */
public interface BenchmarkMonitorDao extends Dao<BenchmarkMonitor> {
    
    List<BenchmarkMonitor> getBySuite(long suiteId);
    
}
