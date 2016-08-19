package sk.mimac.benchroom.api.service;

import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.ScriptDto;
import sk.mimac.benchroom.api.enums.Platform;
import sk.mimac.benchroom.api.enums.ScriptType;
import sk.mimac.benchroom.api.filter.ScriptFilter;

/**
 *
 * @author Milan Fabian
 */
public interface ScriptService {

    ScriptDto getScriptById(long id);

    long insertScript(ScriptDto script);

    void updateScript(ScriptDto script);
    
    Page<ScriptDto> getScriptPage(ScriptFilter filter);
    
    String getScriptForPlatformVersion(Platform platform, long versionId, ScriptType type);
}
