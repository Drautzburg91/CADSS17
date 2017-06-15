package Mqtt.Validator;


import Mqtt.Model.User;
import Mqtt.Model.VHost;
import Mqtt.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Sebastian Thümmel on 15.06.2017.
 */

@Component
public class VHostValidator implements Validator {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return VHost.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        VHost vHost = (VHost) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        User userdb = authenticationService.getUser(vHost.getUsername());
        if(userdb == null){
            errors.rejectValue("username", "NotExists.permissionForm.username");
        }

    }
}
