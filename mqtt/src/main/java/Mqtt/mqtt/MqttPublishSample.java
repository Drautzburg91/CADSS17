package Mqtt.mqtt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Properties;

public class MqttPublishSample {
	static String clientId = "JavaSample";

	private final static String QUEUE_NAME = "hello";
	
	
	
	public static void handlePLZ (String plz,String countryCode){
		
		Properties config = new Properties();

		try {

			System.out.println("Reading API");

			
			String apiId = "41c464d95d33fabc24d44a5086ea9848";
			  String M2MIO_USERNAME = "caduser";
			 String M2MIO_PASSWORD_MD5 = "caduser";

			String urlAPI = "http://api.openweathermap.org/data/2.5/find?q="+ plz + "," + countryCode + "&units=metric" + "&APPID="+ apiId;

			// Connect to the URL using java's native library
			URL url = new URL(urlAPI);
			HttpURLConnection request = (HttpURLConnection) url
					.openConnection();
			request.connect();

			// Convert to a JSON object to print data

			JsonParser jp = new JsonParser(); // from gson
			JsonElement root = jp.parse(new InputStreamReader(
					(InputStream) request.getContent())); // Convert the input stream to a json element
															
			JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.
															
			JsonArray jsonArray = rootobj.getAsJsonArray("list"); // Json request with all information
																	

			System.out.println(jsonArray);

			System.out.println("API reading complete");

			// config.properties erstellen, code aus .example kopieren und bekannte url einsetzen
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(M2MIO_USERNAME);
			options.setPassword(M2MIO_PASSWORD_MD5.toCharArray());
			
			
			config.load(new FileInputStream("config.properties"));
			MqttClient client = new MqttClient(config.getProperty("host"),
					MqttClient.generateClientId());
			
			client.connect(options);
		
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
			//obj.setWindspeed(jsonArray.get(0).getAsJsonObject().get("wind").getAsJsonObject().get("deg").getAsDouble());
			obj.setCurrentWeather(jsonArray.get(0).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());
			obj.setCurrentWeatherId(jsonArray.get(0).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt());
			String jsonInString = gson.toJson(obj);
	
			
			System.out.println(jsonInString);

			MqttMessage message = new MqttMessage(jsonInString.getBytes());
			
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
		}
	}
		
	
	public static void main(String[] args) {
     
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
}
	
	}
	