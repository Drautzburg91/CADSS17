package Mqtt.Validator;

import Mqtt.Model.User;
import Mqtt.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import java.util.Set;

/**
 * Created by Sebastian Thümmel on 14.06.2017.
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private AuthenticationService authenticationService;


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        User userdb = authenticationService.getUser(user.getUsername());
        if(userdb != null){
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if(!user.getPasswordConfirm().equals(user.getPassword())){
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }

    }
}
