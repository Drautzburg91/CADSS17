package Mqtt.Repository;

import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

/**
 * Created by Basti on 13.06.2017.
 */

@Repository("userRepository")
public interface UserRepository {

    String insertSystemUser(String userName, String password, String additionalDescription);
    String insertVHost(String vHostName, String additionalDescription);
    String insertAssigned(String systemUser_userName,
                          String vHost_name,
                          String additionalInformation,
                          boolean readRights,
                          boolean writeRights,
                          boolean configureRights);
    ResultSet selectVHostAll();
    ResultSet selectSystemUserAll();
    ResultSet selectSystemUserByUserName(String userName);
    ResultSet selectAssignedAll();


}
