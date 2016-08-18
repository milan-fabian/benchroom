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
    public List<BenchmarkParameter> getBySuite(long suiteId) {
        TypedQuery<BenchmarkParameter> query = em.createNamedQuery(BenchmarkParameter.GET_BY_SUITE, BenchmarkParameter.class);
        query.setParameter("suiteId", suiteId);
        return query.getResultList();
    }
    
}
