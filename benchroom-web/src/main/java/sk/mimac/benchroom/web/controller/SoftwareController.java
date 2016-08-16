package sk.mimac.benchroom.web.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sk.mimac.benchroom.api.dto.impl.SoftwareDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.filter.SoftwareFilter;
import sk.mimac.benchroom.api.filter.SoftwareVersionFilter;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.web.PageWrapper;
import sk.mimac.benchroom.web.WebConstants;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

    @RequestMapping(value = WebConstants.URL_SOFTWARE, method = RequestMethod.GET)
    public ModelAndView getSoftware() {
        return new ModelAndView("software/software_list");
    }

    @RequestMapping(value = WebConstants.URL_SOFTWARE_VERSION, method = RequestMethod.GET)
    public ModelAndView getSoftwareVersion(@RequestParam("software") long softwareId) {
        Map<String, Object> model = new HashMap<>();
        model.put("software", softwareService.getSoftwareById(softwareId));
        return new ModelAndView("software/version/software_version_list", model);
    }

    @RequestMapping(value = WebConstants.URL_SOFTWARE_EDIT, method = RequestMethod.GET)
    public ModelAndView getSoftwareEdit(@RequestParam(name = "software", required = false) Long softwareId) {
        Map<String, Object> model = new HashMap<>();
        if (softwareId != null) {
            model.put("software", softwareService.getSoftwareById(softwareId));
        } else {
            model.put("software", new SoftwareDto());
        }
        return new ModelAndView("software/software_edit", model);
    }

    @RequestMapping(value = WebConstants.URL_SOFTWARE_EDIT, method = RequestMethod.POST)
    public ModelAndView postSoftwareEdit(@Valid @ModelAttribute(name = "software") SoftwareDto software, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("software/software_edit");
        }
        if (software.getId() == null) {
            softwareService.insertSoftware(software);
        } else {
            softwareService.updateSoftware(software);
        }
        return new ModelAndView("redirect:" + WebConstants.URL_SOFTWARE);
    }

    @RequestMapping(value = WebConstants.URL_SOFTWARE_VERSION_EDIT, method = RequestMethod.GET)
    public ModelAndView getSoftwareVersionEdit(@RequestParam(name = "version", required = false) Long versionId, @RequestParam("software") long softwareId) {
        Map<String, Object> model = new HashMap<>();
        if (versionId != null) {
            model.put("version", softwareService.getVersionById(versionId));
        } else {
            model.put("version", new SoftwareVersionDto());
        }
        model.put("software", softwareService.getSoftwareById(softwareId));
        return new ModelAndView("software/version/software_version_edit", model);
    }

    @RequestMapping(value = WebConstants.URL_SOFTWARE_VERSION_EDIT, method = RequestMethod.POST)
    public ModelAndView postSoftwareVersionEdit(@Valid @ModelAttribute(name = "version") SoftwareVersionDto version, BindingResult result,
            @RequestParam("software") long softwareId) {
        if (result.hasErrors()) {
            Map<String, Object> model = new HashMap<>();
            model.put("software", softwareService.getSoftwareById(softwareId));
            return new ModelAndView("software/version/software_version_edit", model);
        }
        if (version.getId() == null) {
            softwareService.insertVersion(version, softwareId);
        } else {
            softwareService.updateVersion(version);
        }
        return new ModelAndView("redirect:" + WebConstants.URL_SOFTWARE_VERSION + "?software=" + softwareId);
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_SOFTWARE_LIST, method = RequestMethod.GET)
    public PageWrapper getSoftwareList(@RequestParam("length") int pageSize, @RequestParam("start") int start, @RequestParam("search[value]") String fulltext) {
        SoftwareFilter filter = new SoftwareFilter();
        filter.setPageNumber((start / pageSize) + 1);
        filter.setPageSize(pageSize);
        filter.setFulltext(fulltext);
        return new PageWrapper(softwareService.getSoftwarePage(filter));
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_SOFTWARE_VERSION_LIST, method = RequestMethod.GET)
    public PageWrapper getSoftwareVersionList(@RequestParam("software") long softwareId, @RequestParam("length") int pageSize, @RequestParam("start") int start) {
        SoftwareVersionFilter filter = new SoftwareVersionFilter();
        filter.setSoftwareId(softwareId);
        filter.setPageNumber((start / pageSize) + 1);
        filter.setPageSize(pageSize);
        return new PageWrapper(softwareService.getVersionPage(filter));
    }
}
