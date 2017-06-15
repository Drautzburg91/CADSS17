package Mqtt.Service;

import org.springframework.stereotype.Service;

/**
 * Created by Sebastian Thümmel on 14.06.2017.
 */
@Service
public interface SecurityService {

    String findLoggedInUsername();
    void autologin(String username, String password);
}
