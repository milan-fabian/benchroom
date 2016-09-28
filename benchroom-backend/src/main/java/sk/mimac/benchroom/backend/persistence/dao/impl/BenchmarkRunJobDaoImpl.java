package sk.mimac.benchroom.backend.persistence.dao.impl;

import org.springframework.stereotype.Component;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkRunJobDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRunJob;

/**
 *
 * @author Milan Fabian
 */
@Component
public class BenchmarkRunJobDaoImpl extends AbstractDao<BenchmarkRunJob> implements BenchmarkRunJobDao{

    public BenchmarkRunJobDaoImpl() {
        super(BenchmarkRunJob.class);
    }
    
}
