package sk.mimac.benchroom.backend.persistence.dao.impl;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkParameterDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkParameter;

/**
 *
 * @author Milan Fabian
 */
@Component
public class BenchmarkParameterDaoImpl extends AbstractDao<BenchmarkParameter> implements BenchmarkParameterDao{

    public BenchmarkParameterDaoImpl() {
        super(BenchmarkParameter.class);
    }

    @Override
    public List<BenchmarkParameter> getBySuitePositionPriority(long suiteId, short position, short minPriority) {
        TypedQuery<BenchmarkParameter> query = em.createNamedQuery(BenchmarkParameter.GET_BY_SUITE_POSITION_PRIORITY, BenchmarkParameter.class);
        query.setParameter("suiteId", suiteId);
        query.setParameter("position", position);
        query.setParameter("minPriority", minPriority);
        return query.getResultList();
    }
    
}
