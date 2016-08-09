package sk.mimac.benchroom.backend.persistence.dao.impl;

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

}
