package sk.mimac.benchroom.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sk.mimac.benchroom.web.WebConstants;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class HomeController {

    @RequestMapping(value = WebConstants.URL_HOME, method = RequestMethod.GET)
    public ModelAndView getHome() {
        return new ModelAndView("index");
    }

}
