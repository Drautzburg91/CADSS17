package Mqtt.Controller;

import Mqtt.Model.VHost;
import Mqtt.Service.AuthenticationService;
import Mqtt.Service.MomService;
import Mqtt.Service.SecurityService;
import Mqtt.Validator.VHostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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

    @Autowired
    private MomService momService;

    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public String permission(Model model){
        model.addAttribute("permissionForm", new VHost());

        return "permission";
    }

    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public String permission(@ModelAttribute("permissionForm")VHost vHostForm, BindingResult bindingResult, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        vHostValidator.validate(vHostForm, bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }

        momService.createVhost(authenticationService.getUser(principal.getName()), authenticationService.getUser(vHostForm.getUsername()),vHostForm);
        momService.setPermission(authenticationService.getUser(principal.getName()), authenticationService.getUser(vHostForm.getUsername()),vHostForm);
        authenticationService.createPermission(vHostForm);

        return "permission";
    }

}
