package Mqtt.Model;

/**
 * Created by Basti on 13.06.2017.
 */
public class VHost {

    private String vHostName;
    private String username;

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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public boolean isConfigure() {
        return configure;
    }

    public void setConfigure(boolean configure) {
        this.configure = configure;
    }
}
