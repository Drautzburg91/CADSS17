package Mqtt.Model;

/**
 * Created by Basti on 13.06.2017.
 */
public class VHost {

    private String vHostName;
    private String username;
    private String[] permissions;
    private boolean read;
    private boolean write;
    private boolean configure;

    public String getvHostName() {
        return vHostName;
    }

    public void setvHostName(String vHostName) {
        this.vHostName = vHostName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
}
