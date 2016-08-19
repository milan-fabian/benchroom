package sk.mimac.benchroom.web.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sk.mimac.benchroom.api.dto.impl.ScriptDto;
import sk.mimac.benchroom.api.filter.ScriptFilter;
import sk.mimac.benchroom.api.service.ScriptService;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.web.PageWrapper;
import sk.mimac.benchroom.web.WebConstants;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private SoftwareService softwareService;

    @RequestMapping(value = WebConstants.URL_SCRIPT, method = RequestMethod.GET)
    public ModelAndView getScript(@RequestParam("version") long versionId) {
        Map<String, Object> model = new HashMap<>();
        model.put("version", softwareService.getVersionById(versionId));
        return new ModelAndView("script/script_list", model);
    }

    @RequestMapping(value = WebConstants.URL_SCRIPT_EDIT, method = RequestMethod.GET)
    public ModelAndView getScriptEdit(@RequestParam(name = "script", required = false) Long scriptId, @RequestParam("version") long versionId) {
        Map<String, Object> model = new HashMap<>();
        if (scriptId != null) {
            model.put("script", scriptService.getScriptById(scriptId));
        } else {
            model.put("script", new ScriptDto());
        }
        model.put("version", softwareService.getVersionById(versionId));
        return new ModelAndView("script/script_edit", model);
    }

    @RequestMapping(value = WebConstants.URL_SCRIPT_EDIT, method = RequestMethod.POST)
    public ModelAndView postSciptEdit(@Valid @ModelAttribute(name = "script") ScriptDto script, BindingResult result,
            @RequestParam("version") long versionId) {
        if (result.hasErrors()) {
            Map<String, Object> model = new HashMap<>();
            model.put("version", softwareService.getVersionById(versionId));
            return new ModelAndView("script/script_edit", model);
        }
        script.setSoftwareVersionId(versionId);
        if (script.getId() == null) {
            scriptService.insertScript(script);
        } else {
            scriptService.updateScript(script);
        }
        return new ModelAndView("redirect:" + WebConstants.URL_SCRIPT + "?version=" + versionId);
    }

    @ResponseBody
    @RequestMapping(value = WebConstants.URL_SCRIPT_LIST, method = RequestMethod.GET)
    public PageWrapper getScriptList(@RequestParam("version") long versionId, @RequestParam("length") int pageSize, @RequestParam("start") int start) {
        ScriptFilter filter = new ScriptFilter();
        filter.setSoftwareVersionId(versionId);
        filter.setPageNumber((start / pageSize) + 1);
        filter.setPageSize(pageSize);
        return new PageWrapper(scriptService.getScriptPage(filter));
    }

}
