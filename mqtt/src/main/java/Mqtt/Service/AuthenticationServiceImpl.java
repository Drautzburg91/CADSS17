package Mqtt.Service;

import Mqtt.Model.User;
import Mqtt.Model.UserDao;
import Mqtt.Model.UserDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Basti on 13.06.2017.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    UserDao userDao = new UserDaoImpl();

    @Override
    public void createUser(String username, String password, List<String> vHosts) {
        this.userDao.insertSystemUser(username,password);
        for(String vHost : vHosts){
            this.userDao.insertAssigned(username,vHost);
            // TODO: 13.06.2017 Permissions einfügen
        }
    }

    @Override
    public List<User> getUserList() {
        List list = new ArrayList();
        ResultSet rs = this.userDao.selectSystemUserAll();
        try {
            while (rs.next()){
                User user = new User();
                user.setUsername(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User getUser(String username) {
        ResultSet rs = this.userDao.selectSystemUserPwByUserName(username);
        User user = null;
        try {
            user = new User();
            user.setUsername(rs.getString("userName"));
            user.setPassword(rs.getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
