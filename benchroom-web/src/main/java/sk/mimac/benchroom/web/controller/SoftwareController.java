package sk.mimac.benchroom.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sk.mimac.benchroom.api.service.SoftwareService;

/**
 *
 * @author Milan Fabian
 */
@Controller
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

}
