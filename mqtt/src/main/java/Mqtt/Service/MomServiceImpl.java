package Mqtt.Service;

import Mqtt.Model.User;
import Mqtt.Model.VHost;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Basti on 15.06.2017.
 */

@Service
public class MomServiceImpl implements MomService {

    @Autowired
    private AuthenticationService authenticationService;

    Gson gson;

    public MomServiceImpl(){
        gson = new Gson();
    }

    public String addUser(User loggedInUser, User user){
        String stringUrl = "http://"+System.getenv("CadRabbit_Host")+":15672/api/users/"+ user.getUsername();
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("X-Requested-With", "Curl");
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            String userpass = "cadadmin:cadadmin";
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
            connection.setRequestProperty("Authorization", basicAuth);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            HashMap<String, String> jsonMap = new HashMap<>();
            jsonMap.put("password",user.getPassword());
            if(user.isAdmin()){
                jsonMap.put("tags","administrator");
            } else {
                jsonMap.put("tags", "");
            }

            writer.write(gson.toJson(jsonMap));
            writer.flush();
            String line;
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            writer.close();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    public String setPermission(User loggedInUser, User user, VHost vHost){
        String stringUrl = "http://"+System.getenv("CadRabbit_Host")+":15672/api/permissions/"+vHost.getvHostName()+"/"+vHost.getUsername();
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("X-Requested-With", "Curl");
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            String userpass = user.getUsername()+":"+user.getPassword();
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
            connection.setRequestProperty("Authorization", basicAuth);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            HashMap<String, String> jsonMap = new HashMap<>();
            for(String permission : vHost.getPermissions()){
                jsonMap.put(permission,".*");
            }

            writer.write(gson.toJson(jsonMap));
            writer.flush();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(jsonMap));
            String line;
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            writer.close();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    public String createVhost(User loggedInUser, User user, VHost vHost){
        String stringUrl = "http://"+System.getenv("CadRabbit_Host")+":15672/api/vhosts/"+ vHost.getvHostName();
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("X-Requested-With", "Curl");
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            String userpass = user.getUsername()+":"+user.getPassword();
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
            connection.setRequestProperty("Authorization", basicAuth);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            HashMap<String, String> jsonMap = new HashMap<>();
            jsonMap.put("tracing","true");
            writer.write(gson.toJson(jsonMap));
            writer.flush();
            String line;
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            writer.close();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        return "success";
    }

    public void writeSkript(){
        List<User> users = authenticationService.getUserList();
        List<VHost> vHosts = authenticationService.getVhosts();
        File skript = new File ("init.sh");
        try {
            FileOutputStream fos = new FileOutputStream(skript);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write("#!/bin/sh");
            writer.write(System.getProperty("line.separator"));
            writer.write("( sleep 5 ; \\");
            writer.write(System.getProperty("line.separator"));
            for(User user : users){
                writer.write("rabbitmqctl add_user "+user.getUsername()+" "+user.getPassword()+" 2>/dev/null ; \\");
                writer.write(System.getProperty("line.separator"));
                if(user.isAdmin()){
                    writer.write("rabbitmqctl set_user_tags "+user.getUsername()+" administrator ; \\");
                    writer.write(System.getProperty("line.separator"));
                }
            }
            for(VHost vHost : vHosts){
                writer.write("rabbitmqctl set_permissions -p "+vHost.getvHostName()+" "+vHost.getUsername()+ "\".*\" \".*\" \".*\" ; \\");
                writer.write(System.getProperty("line.separator"));
            }
            writer.write(") &");
            writer.write(System.getProperty("line.separator"));
            writer.write("rabbitmq-server $@");
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
