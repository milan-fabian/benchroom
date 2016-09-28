package sk.mimac.benchroom.backend.persistence.dao.impl;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import sk.mimac.benchroom.backend.persistence.dao.ExecutorDao;
import sk.mimac.benchroom.backend.persistence.entity.Executor;

/**
 *
 * @author Milan Fabian
 */
@Component
public class ExecutorDaoImpl extends AbstractDao<Executor> implements ExecutorDao {

    public ExecutorDaoImpl() {
        super(Executor.class);
    }

    @Override
    public Executor getBySystemInfo(long systemInfoId) {
        TypedQuery<Executor> query = em.createNamedQuery(Executor.GET_BY_SYSTEM_INFO, Executor.class);
        query.setParameter("systemInfoId", systemInfoId);
        List<Executor> list = query.getResultList();
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }

}
