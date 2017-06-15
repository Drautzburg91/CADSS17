package Mqtt.Controller;

import Mqtt.Model.User;
import Mqtt.Service.AuthenticationService;
import Mqtt.Service.MomService;
import Mqtt.Service.SecurityService;
import Mqtt.Validator.UserValidator;
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
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MomService momService;

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
        String status = authenticationService.createUser(userForm.getPassword(),userForm.getUsername());
        if(status.equals("Successfull")){
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




}
