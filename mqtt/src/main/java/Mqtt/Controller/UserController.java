package Mqtt.Controller;

import Mqtt.Model.User;
import Mqtt.Model.VHost;
import Mqtt.Service.AuthenticationService;
import Mqtt.Service.MomService;
import Mqtt.Service.SecurityService;
import Mqtt.Validator.UserValidator;
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
 * Created by Sebastian Thümmel on 13.06.2017.
 */

@Controller
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MomService momService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private VHostValidator vHostValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm")User userForm, BindingResult bindingResult, Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();

        userValidator.validate(userForm, bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        String status = authenticationService.createUser(userForm);
        if(status.contains("Successfull")){
            momService.addUser(authenticationService.getUser(principal.getName()), userForm);
            momService.writeSkript();
            model.addAttribute("message", userForm.getUsername()+" successful created");
        } else {
            model.addAttribute("error", "error creating "+userForm.getUsername());
        }

        return "registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout){
        if(error != null && !error.isEmpty()){
            model.addAttribute("error", "Invalid username and password");
        }
        if(logout != null){
            model.addAttribute("message", "Logout successful");
        }

        return "login";
    }

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

        authenticationService.addPermission(vHostForm);
        String statusCreate = momService.createVhost(authenticationService.getUser(principal.getName()), authenticationService.getUser(vHostForm.getUsername()),vHostForm);
        String statusPermission = momService.setPermission(authenticationService.getUser(principal.getName()), authenticationService.getUser(vHostForm.getUsername()),vHostForm);
        if(statusCreate.equals("error") || statusPermission.equals("error")){
            model.addAttribute("error", "Permission not created, try again");
        } else {
            momService.writeSkript();
            model.addAttribute("message", "Permission successful created");
        }

        return "permission";
    }


}
