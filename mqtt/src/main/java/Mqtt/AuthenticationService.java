package Mqtt;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Basti on 13.06.2017.
 */
@Service
public interface AuthenticationService {

    void createUser(String username, String password, List<String> vHosts);
    List<User> getUserList();
    User getUser(String username);

}
