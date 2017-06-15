package Mqtt.Service;

import Mqtt.Model.User;
import org.springframework.stereotype.Service;

/**
 * Created by Basti on 15.06.2017.
 */

@Service
public interface MomService {

    String addUser(User loggedInUser, User user);

}
