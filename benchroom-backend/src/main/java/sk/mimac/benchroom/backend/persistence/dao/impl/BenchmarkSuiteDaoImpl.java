package sk.mimac.benchroom.backend.persistence.dao.impl;

import org.springframework.stereotype.Component;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkSuiteDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkSuite;

/**
 *
 * @author Milan Fabian
 */
@Component
public class BenchmarkSuiteDaoImpl extends AbstractDao<BenchmarkSuite> implements BenchmarkSuiteDao {

    public BenchmarkSuiteDaoImpl() {
        super(BenchmarkSuite.class);
    }

}
