package sk.mimac.benchroom.backend.persistence.dao.impl;

import java.util.List;
import javax.persistence.TypedQuery;
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

    @Override
    public List<BenchmarkMonitor> getBySuite(long suiteId) {
        TypedQuery<BenchmarkMonitor> query = em.createNamedQuery(BenchmarkMonitor.GET_BY_SUITE, BenchmarkMonitor.class);
        query.setParameter("suiteId", suiteId);
        return query.getResultList();
    }
}
