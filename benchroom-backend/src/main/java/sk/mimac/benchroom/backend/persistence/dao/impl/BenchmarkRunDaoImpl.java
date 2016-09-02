package sk.mimac.benchroom.backend.persistence.dao.impl;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkRunDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRun;

/**
 *
 * @author Milan Fabian
 */
@Component
public class BenchmarkRunDaoImpl extends AbstractDao<BenchmarkRun> implements BenchmarkRunDao {

    public BenchmarkRunDaoImpl() {
        super(BenchmarkRun.class);
    }

    @Override
    public List<BenchmarkRun> getByIds(List<Long> ids) {
        TypedQuery<BenchmarkRun> query = em.createNamedQuery(BenchmarkRun.GET_BY_IDS, BenchmarkRun.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}
