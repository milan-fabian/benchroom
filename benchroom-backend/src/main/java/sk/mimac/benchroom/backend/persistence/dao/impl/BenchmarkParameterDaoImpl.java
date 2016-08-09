package sk.mimac.benchroom.backend.persistence.dao.impl;

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
    
}
