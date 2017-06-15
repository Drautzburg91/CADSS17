package Mqtt.Model;

import java.util.List;

/**
 * Created by Sebastian Thümmel on 13.06.2017.
 */
public class User {

    private String username;
    private String password;
    private String passwordConfirm;
    private List<VHost> vHostList;
    private boolean admin;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public List<VHost> getvHostList() {
        return vHostList;
    }

    public void setvHostList(List<VHost> vHostList) {
        this.vHostList = vHostList;
    }

}
