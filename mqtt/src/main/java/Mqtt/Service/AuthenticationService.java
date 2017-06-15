package Mqtt.Service;

import Mqtt.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Basti on 13.06.2017.
 */
@Service
public interface AuthenticationService {

    void createUser(String username, String password);
    void createUser(String username, String password, String additionalInformation);
    List<User> getUserList();
    User getUser(String username);

}
