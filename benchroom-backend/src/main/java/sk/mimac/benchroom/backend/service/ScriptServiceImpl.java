package sk.mimac.benchroom.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.ScriptDto;
import sk.mimac.benchroom.api.enums.Platform;
import sk.mimac.benchroom.api.enums.ScriptType;
import sk.mimac.benchroom.api.filter.ScriptFilter;
import sk.mimac.benchroom.api.service.ScriptService;
import sk.mimac.benchroom.backend.persistence.dao.ScriptDao;
import sk.mimac.benchroom.backend.persistence.entity.Script;
import sk.mimac.benchroom.backend.persistence.query.ScriptQueryBuilder;
import sk.mimac.benchroom.backend.utils.ConvertUtils;

/**
 *
 * @author Milan Fabian
 */
@Service
@Transactional(readOnly = true)
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptDao scriptDao;

    @Override
    public ScriptDto getScriptById(long id) {
        return ConvertUtils.convert(scriptDao.find(id));
    }

    @Override
    @Transactional(readOnly = false)
    public long insertScript(ScriptDto script) {
        return scriptDao.insert(ConvertUtils.convert(script));
    }

    @Override
    @Transactional(readOnly = false)
    public void updateScript(ScriptDto script) {
        scriptDao.update(ConvertUtils.convert(script));
    }

    @Override
    public Page<ScriptDto> getScriptPage(ScriptFilter filter) {
        ScriptQueryBuilder queryBuilder = new ScriptQueryBuilder(filter);
        long count = scriptDao.countForFilter(queryBuilder);
        List<ScriptDto> list = new ArrayList<>(filter.getPageSize());
        for (Script script : scriptDao.getForFilter(queryBuilder)) {
            list.add(ConvertUtils.convert(script));
        }
        return new Page(list, filter.getPageNumber(), filter.getPageSize(), count);
    }

    @Override
    public String getScriptForPlatformVersion(Platform platform, long versionId, ScriptType type) {
        return scriptDao.getByVersionPlatformType(versionId, platform, type);
    }

}
