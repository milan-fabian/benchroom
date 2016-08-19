package sk.mimac.benchroom.api.filter;

import sk.mimac.benchroom.api.dto.impl.ScriptDto;

/**
 *
 * @author Milan Fabian
 */
public class ScriptFilter extends AbstractFilter<ScriptDto> {

    private Long softwareVersionId;

    public Long getSoftwareVersionId() {
        return softwareVersionId;
    }

    public void setSoftwareVersionId(Long softwareVersionId) {
        this.softwareVersionId = softwareVersionId;
    }

}
