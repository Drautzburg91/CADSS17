package Mqtt;

import com.google.gson.*;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Sebastian Thümmel on 22.05.2017.
 */

@Service
public class MqttService {

    private String apiId = "41c464d95d33fabc24d44a5086ea9848";
    private String M2MIO_USERNAME = "caduser";
    private String M2MIO_PASSWORD_MD5 = "caduser";
    private Properties config;
    private boolean liveSource;

    private MqttConnectOptions options;
    private MqttClient client;


    public MqttService(){
        // config.properties erstellen, code aus .example kopieren und bekannte url einsetzen
        options = new MqttConnectOptions();
        options.setUserName(M2MIO_USERNAME);
        options.setPassword(M2MIO_PASSWORD_MD5.toCharArray());
        config  = new Properties();
        liveSource = true;

        try {
            config.load(new FileInputStream("config.properties"));
            client = new MqttClient(config.getProperty("host"), MqttClient.generateClientId());
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Async
    public void publishWeatherData(){
        if(liveSource){
            while(true){
                handlePLZ("78467","de");
                handlePLZ("10115","de");
                handlePLZ("20095","de");
                handlePLZ("50679","de");
                try{
                    Thread.sleep(2000);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } else {
            sendFakeData("78467","DE");
        }

    }

    public void sendFakeData(String plz, String countryCode){

    }

    public void handlePLZ (String plz,String countryCode){
        MqttMessage message;
        String jsonInString;

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

            Gson gson = new Gson();
            WeatherData obj = new WeatherData();

            obj.setCityName(jsonArray.get(0).getAsJsonObject().get("name").getAsString());
            obj.setLongitude(jsonArray.get(0).getAsJsonObject().get("coord").getAsJsonObject().get("lon").getAsDouble());
            obj.setLatitude(jsonArray.get(0).getAsJsonObject().get("coord").getAsJsonObject().get("lat").getAsDouble());
            obj.setHumitidy(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsInt());
            obj.setPressure(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("pressure").getAsInt());
            obj.setTemperature(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble());
            obj.setTemperatureMax(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp_max").getAsDouble());
            obj.setTemperatureMin(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("temp_min").getAsDouble());
            obj.setWindspeed(jsonArray.get(0).getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsDouble());
            obj.setWindspeed(jsonArray.get(0).getAsJsonObject().get("wind").getAsJsonObject().get("deg").getAsDouble());
            obj.setCurrentWeather(jsonArray.get(0).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());
            obj.setCurrentWeatherId(jsonArray.get(0).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt());
            obj.setWeatherIcon(jsonArray.get(0).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString());
            obj.setPlz(plz);

            jsonInString = gson.toJson(obj);
            System.out.println(jsonInString);

            message = new MqttMessage(jsonInString.getBytes());

            client.publish("today",message);
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




}
