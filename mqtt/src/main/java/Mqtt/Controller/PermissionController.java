package Mqtt.Controller;

import Mqtt.Model.VHost;
import Mqtt.Service.AuthenticationService;
import Mqtt.Service.SecurityService;
import Mqtt.Validator.VHostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Sebastian Thümmel on 15.06.2017.
 */

@Controller
public class PermissionController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private VHostValidator vHostValidator;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public String permission(Model model){
        model.addAttribute("permissionForm", new VHost());

        return "permission";
    }

    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public String permission(@ModelAttribute("permissionForm")VHost vHostForm, BindingResult bindingResult, Model model){
        vHostValidator.validate(vHostForm, bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        authenticationService.createPermission(vHostForm);

        return "permission";
    }

}
