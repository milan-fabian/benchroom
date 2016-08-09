package sk.mimac.benchroom.api.filter;

import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;

/**
 *
 * @author Milan Fabian
 */
public class SoftwareVersionFilter extends AbstractFilter<SoftwareVersionDto> {

    private Long softwareId;

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

}
