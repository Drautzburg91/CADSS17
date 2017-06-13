package Mqtt.Model;

import java.util.List;

/**
 * Created by Basti on 13.06.2017.
 */
public class User {

    private String username;
    private String password;
    private List<VHost> vHostList;

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

    public List<VHost> getvHostList() {
        return vHostList;
    }

    public void setvHostList(List<VHost> vHostList) {
        this.vHostList = vHostList;
    }
}
