package sk.mimac.benchroom.api.service;

import java.util.Map;
import sk.mimac.benchroom.api.dto.impl.SystemInfoDto;
import sk.mimac.benchroom.api.system.SystemParameter;

/**
 *
 * @author Milan Fabian
 */
public interface SystemInfoService {

    long insertInfo(SystemInfoDto systemInfo);
    
    long getOrCreateInfo(Map<SystemParameter, String> parameters);
}
