package Mqtt.Service;

import Mqtt.Model.User;
import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


/**
 * Created by Basti on 15.06.2017.
 */

@Service
public class MomServiceImpl implements MomService {

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
                jsonMap.put("tags", "administrator");
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

}
