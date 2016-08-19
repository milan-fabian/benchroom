package sk.mimac.benchroom.backend.persistence.dao;

import sk.mimac.benchroom.api.enums.Platform;
import sk.mimac.benchroom.api.enums.ScriptType;
import sk.mimac.benchroom.backend.persistence.entity.Script;

/**
 *
 * @author Milan Fabian
 */
public interface ScriptDao extends Dao<Script> {

    String getByVersionPlatformType(long versionId, Platform platform, ScriptType type);
}
