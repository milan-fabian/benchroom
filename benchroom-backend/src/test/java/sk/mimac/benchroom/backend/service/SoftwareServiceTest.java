package sk.mimac.benchroom.backend.service;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.SoftwareDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.filter.SoftwareFilter;
import sk.mimac.benchroom.api.filter.SoftwareVersionFilter;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.backend.config.SpringTestConfig;

/**
 *
 * @author Milan Fabian
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
public class SoftwareServiceTest {

    @Autowired
    private SoftwareService softwareService;

    @Test
    public void softwareManipulationTest() {
        SoftwareDto software = new SoftwareDto();
        software.setName("testSoft");
        long id = softwareService.insertSoftware(software);
        SoftwareFilter filter = new SoftwareFilter();
        filter.setFulltext("testSoft");
        filter.setPageNumber(1);
        filter.setPageSize(10);
        Page<SoftwareDto> page = softwareService.getSoftwarePage(filter);
        assertEquals(1, page.getElements().size());
        assertEquals(1, page.getTotalElements());
        assertEquals("testSoft", page.getElements().get(0).getName());

        SoftwareVersionDto version = new SoftwareVersionDto();
        version.setName("ver 1.0");
        softwareService.insertVersion(version, id);
        SoftwareVersionFilter filter2 = new SoftwareVersionFilter();
        filter2.setSoftwareId(id);
        filter2.setPageNumber(1);
        filter2.setPageSize(10);
        Page<SoftwareVersionDto> page2 = softwareService.getVersionPage(filter2);
        assertEquals(1, page2.getElements().size());
        assertEquals(1, page2.getTotalElements());
        assertEquals("ver 1.0", page2.getElements().get(0).getName());
    }

}
