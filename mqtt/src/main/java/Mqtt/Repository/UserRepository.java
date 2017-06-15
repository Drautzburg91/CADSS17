package Mqtt.Repository;

import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

/**
 * Created by Basti on 13.06.2017.
 */

@Repository("userRepository")
public interface UserRepository {

    String insertSystemUser(String userName, String password, String additionalDescription);
    String insertSystemUser(String userName, String password);
    String insertVHost(String vHostName, String additionalDescription);
    String insertAssigned(String systemUserUserName, String vHostName, String additionalInformation);
    String insertAssigned(String systemUserUserName, String vHostName);
    ResultSet selectVHostAll();
    ResultSet selectSystemUserAll();
    ResultSet selectSystemUserPwByUserName(String userName);
    ResultSet selectAssignedAll();
    ResultSet selectAssignedUserByVHost(String vHostName);


}