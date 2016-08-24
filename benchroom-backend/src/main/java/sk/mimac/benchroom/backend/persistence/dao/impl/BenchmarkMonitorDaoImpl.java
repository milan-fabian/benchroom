package sk.mimac.benchroom.backend.persistence.dao.impl;

import org.springframework.stereotype.Component;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkMonitorDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkMonitor;

/**
 *
 * @author Milan Fabian
 */
@Component
public class BenchmarkMonitorDaoImpl extends AbstractDao<BenchmarkMonitor> implements BenchmarkMonitorDao {

    public BenchmarkMonitorDaoImpl() {
        super(BenchmarkMonitor.class);
    }

}
