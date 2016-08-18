package sk.mimac.benchroom.backend.persistence.dao;

import java.util.List;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkParameter;

/**
 *
 * @author Milan Fabian
 */
public interface BenchmarkParameterDao extends Dao<BenchmarkParameter> {
    
    List<BenchmarkParameter> getBySuite(long suiteId);
    
}
