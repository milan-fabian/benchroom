package sk.mimac.benchroom.backend.persistence.dao;

import java.util.List;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRun;

/**
 *
 * @author Milan Fabian
 */
public interface BenchmarkRunDao extends Dao<BenchmarkRun> {

    List<BenchmarkRun> getByIds(List<Long> ids);

}
