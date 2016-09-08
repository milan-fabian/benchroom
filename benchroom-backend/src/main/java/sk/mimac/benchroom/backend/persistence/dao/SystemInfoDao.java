package sk.mimac.benchroom.backend.persistence.dao;

import java.util.Map;
import sk.mimac.benchroom.api.system.SystemParameter;
import sk.mimac.benchroom.backend.persistence.entity.SystemInfo;

/**
 *
 * @author Milan Fabian
 */
public interface SystemInfoDao extends Dao<SystemInfo> {

    
    Long getWithSameParameters(Map<SystemParameter, String> parameters);
}
