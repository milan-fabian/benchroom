package sk.mimac.benchroom.api.service;

import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.SoftwareDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.filter.SoftwareFilter;
import sk.mimac.benchroom.api.filter.SoftwareVersionFilter;

/**
 *
 * @author Milan Fabian
 */
public interface SoftwareService {

    SoftwareDto getSoftwareById(long id);

    long insertSoftware(SoftwareDto software);

    void updateSoftware(SoftwareDto software);

    Page<SoftwareDto> getSoftwarePage(SoftwareFilter filter);
    
    SoftwareVersionDto getVersionById(long id);
    
    long insertVersion(SoftwareVersionDto version);
    
    void updateVersion(SoftwareVersionDto version);

    Page<SoftwareVersionDto> getVersionPage(SoftwareVersionFilter filter);
    
}
