package sk.mimac.benchroom.backend.persistence.dao;

import sk.mimac.benchroom.backend.persistence.entity.Executor;

/**
 *
 * @author Milan Fabian
 */
public interface ExecutorDao extends Dao<Executor> {

    Executor getBySystemInfo(long systemInfoId);
}
