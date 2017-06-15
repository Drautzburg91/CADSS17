package Mqtt.Service;

import Mqtt.Model.User;
import Mqtt.Model.VHost;
import Mqtt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian Thümmel on 13.06.2017.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String createUser(String username, String password) {
        return this.userRepository.insertSystemUser(username,bCryptPasswordEncoder.encode(password));
    }

    public void addPermission(String username, String vHost){
        createVhost(vHost);
        this.userRepository.insertAssigned(username,vHost, null);
    }

    @Override
    public String createUser(String username, String password, String additionalInformation) {
        boolean isAdmin = additionalInformation.equalsIgnoreCase("Administrator");
        if(isAdmin){
            return this.userRepository.insertSystemUser(username,bCryptPasswordEncoder.encode(password),"Administrator");
        } else {
            return this.userRepository.insertSystemUser(username,bCryptPasswordEncoder.encode(password));
        }

    }

    private void createVhost(String vHost) {
        this.userRepository.insertVHost(vHost,null);
    }


    @Override
    public List<User> getUserList() {
        List list = new ArrayList();
        ResultSet rs = this.userRepository.selectSystemUserAll();

        try {
            while (rs.next()){
                User user = new User();
                user.setUsername(rs.getString("userName"));
                user.setPassword(rs.getString("passwort"));
                String description = rs.getString("description");
                if(description.equals("Administrator")){
                    user.setAdmin(true);
                }
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User getUser(String username) {
        ResultSet rs = this.userRepository.selectSystemUserPwByUserName(username);

        User user;

        try {
            while(rs.next()){
                user = new User();
                user.setUsername(username);
                user.setPassword(rs.getString("passwort"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VHost> getVhosts(){

        List<VHost> vHosts = new ArrayList<>();
        ResultSet rs = this.userRepository.selectAssignedAll();

        try {
            System.out.println(rs.getMetaData());
            while (rs.next()){
                VHost vHost = new VHost();
                vHost.setUsername(rs.getString("userName"));
                vHost.setvHostName(rs.getString("vHost_name"));
                vHosts.add(vHost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vHosts;
    }
}
