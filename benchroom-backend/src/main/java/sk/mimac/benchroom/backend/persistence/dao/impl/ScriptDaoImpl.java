package sk.mimac.benchroom.backend.persistence.dao.impl;

import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import sk.mimac.benchroom.api.enums.Platform;
import sk.mimac.benchroom.api.enums.ScriptType;
import sk.mimac.benchroom.backend.persistence.dao.ScriptDao;
import sk.mimac.benchroom.backend.persistence.entity.Script;

/**
 *
 * @author Milan Fabian
 */
@Component
public class ScriptDaoImpl extends AbstractDao<Script> implements ScriptDao {

    public ScriptDaoImpl() {
        super(Script.class);
    }

    @Override
    public String getByVersionPlatformType(long versionId, Platform platform, ScriptType type) {
        TypedQuery<String> query = em.createNamedQuery(Script.GET_BY_VERSION_PLATFORM_TYPE, String.class);
        query.setParameter("versionId", versionId);
        query.setParameter("platform", platform);
        query.setParameter("type", type);
        List<String> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
