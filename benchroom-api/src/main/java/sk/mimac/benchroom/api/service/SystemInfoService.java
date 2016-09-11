package sk.mimac.benchroom.api.service;

import java.util.Map;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
public interface SystemInfoService {
    
    long getOrCreateInfo(Map<SystemParameter, String> parameters);
    
    Long getInfoIdByParameters(Map<SystemParameter, String> parameters);
}
