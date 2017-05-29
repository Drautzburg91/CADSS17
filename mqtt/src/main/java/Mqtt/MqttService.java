package Mqtt;

import com.google.gson.*;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import static org.springframework.boot.Banner.Mode.LOG;

/**
 * Created by Sebastian Thümmel on 22.05.2017.
 */

@Service
public class MqttService implements MessagingService {

    private String apiId = "41c464d95d33fabc24d44a5086ea9848";
    private String jsonInString;
    private Gson gson;
    private boolean transmittingLive;
    private boolean transmittingGenerated;

    private MqttConnectOptions options;
    private MqttClient client;
    private MqttMessage message;

    private WeatherData fakeWeatherData;



    public MqttService(){
        // credentials have to be stored in env variables
        options = new MqttConnectOptions();
        options.setUserName(System.getenv("CadRabbit_UserName"));
        options.setPassword(System.getenv("CadRabbit_Password").toCharArray());

        gson = new Gson();

        try {

            System.out.println("Host: "+ System.getenv("CadRabbit_Host"));
            client = new MqttClient("tcp://"+System.getenv("CadRabbit_Host"), MqttClient.generateClientId());
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void publishLiveWeatherData(){
        transmittingLive = true;
        while (transmittingLive) {
            handlePLZ("78467", "de");
            handlePLZ("10115", "de");
            handlePLZ("20095", "de");
            handlePLZ("50679", "de");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return;
    }

    @Async
    public void publishFakeWeatherData(WeatherData weatherData){
        this.fakeWeatherData = weatherData;
        transmittingGenerated = true;

        while (transmittingGenerated){
            try {
                jsonInString = gson.toJson(this.fakeWeatherData);
                message = new MqttMessage(jsonInString.getBytes());
                System.out.println(jsonInString);
                client.publish("today",message);
                Thread.sleep(2000);
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("*published");
        }
        System.out.println("***** publishing cancelled ***** ");
        return;
    }
 


    private void handlePLZ (String plz,String countryCode){

        try {
            System.out.println("Reading API");
            String urlAPI = "http://api.openweathermap.org/data/2.5/find?q="+ plz + "," + countryCode + "&units=metric" + "&APPID="+ apiId;

            // Connect to the URL using java's native library
            URL url = new URL(urlAPI);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convert to a JSON object to print data

            JsonParser jp = new JsonParser(); // from gson
            JsonElement root = jp.parse(new InputStreamReader(
                    (InputStream) request.getContent())); // Convert the input stream to a json element

            JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.

            JsonArray jsonArray = rootobj.getAsJsonArray("list"); // Json request with all information

            System.out.println(jsonArray);

            System.out.println("API reading complete");

            WeatherData result = new WeatherData();

            result.setCityName(jsonArray.get(0).getAsJsonObject().get("name").getAsString());
            result.setLongitude(jsonArray.get(0).getAsJsonObject().get("coord").getAsJsonObject().get("lon").getAsDouble());
            result.setLatitude(jsonArray.get(0).getAsJsonObject().get("coord").getAsJsonObject().get("lat").getAsDouble());
            result.setHumitidy(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsInt());
            result.setPressure(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("pressure").getAsInt());
            result.setTemperature(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble());
            result.setTemperatureMax(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp_max").getAsDouble());
            result.setTemperatureMin(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp_min").getAsDouble());
            result.setWindspeed(jsonArray.get(0).getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsDouble());
            result.setCurrentWeather(jsonArray.get(0).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());
            result.setCurrentWeatherId(jsonArray.get(0).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt());
            result.setWeatherIcon(jsonArray.get(0).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString());
            result.setPlz(plz);

            JsonElement winDeg = jsonArray.get(0).getAsJsonObject().get("wind").getAsJsonObject().get("deg");
            //System.out.println("WinDeg: " + winDeg.toString());
            
            if(winDeg ==null){
            	
            	result.setWindDeg(0.0);
            }else 
            	result.setWindDeg(winDeg.getAsDouble());
           
            jsonInString = gson.toJson(result);
            System.out.println(jsonInString);

            message = new MqttMessage(jsonInString.getBytes());

            client.publish(plz+"/today",message);
            System.out.println("*published");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttSecurityException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean isTransmittingLive() {
        return transmittingLive;
    }

    public void setTransmittingLive(boolean transmittingLive) {
        this.transmittingLive = transmittingLive;
    }

    public boolean isTransmittingGenerated() {
        return transmittingGenerated;
    }

    public void setTransmittingGenerated(boolean transmittingGenerated) {
        this.transmittingGenerated = transmittingGenerated;
    }
}
