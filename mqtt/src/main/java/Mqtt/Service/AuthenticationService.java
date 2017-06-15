package Mqtt.Service;

import Mqtt.Model.User;
import Mqtt.Model.VHost;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Basti on 13.06.2017.
 */
@Service
public interface AuthenticationService {

    String createUser(String username, String password);
    String createUser(String username, String password, String additionalInformation);
    void addPermission(String username, String vHost);
    List<User> getUserList();
    User getUser(String username);
    List<VHost> getVhosts();

}
