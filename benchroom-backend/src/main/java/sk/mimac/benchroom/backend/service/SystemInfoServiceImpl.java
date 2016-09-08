package sk.mimac.benchroom.backend.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.mimac.benchroom.api.dto.impl.SystemInfoDto;
import sk.mimac.benchroom.api.service.SystemInfoService;
import sk.mimac.benchroom.api.system.SystemParameter;
import sk.mimac.benchroom.backend.persistence.dao.SystemInfoDao;
import sk.mimac.benchroom.backend.persistence.entity.SystemInfo;
import sk.mimac.benchroom.backend.utils.ConvertUtils;

/**
 *
 * @author Milan Fabian
 */
@Service
@Transactional(readOnly = true)
public class SystemInfoServiceImpl implements SystemInfoService {

    @Autowired
    private SystemInfoDao systemInfoDao;

    @Override
    @Transactional(readOnly = false)
    public long insertInfo(SystemInfoDto systemInfo) {
        return systemInfoDao.insert(ConvertUtils.convert(systemInfo));
    }

    @Override
    @Transactional(readOnly = false)
    public long getOrCreateInfo(Map<SystemParameter, String> parameters) {
        Long id = systemInfoDao.getWithSameParameters(parameters);
        if (id == null) {
            SystemInfo entity = new SystemInfo();
            entity.setParameters(parameters);
            return systemInfoDao.insert(entity);
        } else {
            return id;
        }
    }

}
