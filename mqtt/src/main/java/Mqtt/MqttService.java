package Mqtt;

import com.google.gson.*;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sebastian Thümmel and Paul Drautzburg on 22.05.2017.
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
	private MqttCallback callback;

	private WeatherData fakeWeatherData;

	public MqttService() {
		System.out.println("MqttService started");
		// credentials have to be stored in env variables
		options = new MqttConnectOptions();
		options.setUserName(System.getenv("CadRabbit_UserName"));
		options.setPassword(System.getenv("CadRabbit_Password").toCharArray());

		gson = new Gson();

		try {

			System.out.println("Host: " + System.getenv("CadRabbit_Host"));
			client = new MqttClient("tcp://" + System.getenv("CadRabbit_Host"), MqttClient.generateClientId());
			client.connect(options);
			callback = new MqttCallback() {
				@Override
				public void connectionLost(Throwable throwable) {
					try {
						System.out.println("MqttService: connection lost - reconnecting");
						client.connect(options);
						System.out.println("MqttService: connection lost - reconnection successful ");
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

				}

			};

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Async
	public void publishLiveWeatherData() {
		System.out.println("Mqtt-Service: publishLiveWeatherData started");
		transmittingLive = true;
		while (transmittingLive) {
			handlePLZWeekly("78467", "de");// Konstanz
			handlePLZWeekly("40213", "de");// Düsseldorf
			handlePLZWeekly("80331", "de");// München
			handlePLZWeekly("70173", "de");// Stuttgart
			handlePLZWeekly("30159", "de");// Hannover
			handlePLZWeekly("65183", "de");// Wiesbaden
			handlePLZWeekly("01069", "de");// Dresden
			handlePLZWeekly("55116", "de");// Mainz
			handlePLZWeekly("10785", "de");// Berlin
			handlePLZWeekly("24103", "de");// Kiel
			handlePLZWeekly("14467", "de");// Potsdam
			handlePLZWeekly("39104", "de");// Magdeburg
			handlePLZWeekly("99084", "de");// Erfurt
			handlePLZWeekly("20095", "de");// Hamburg
			handlePLZWeekly("19055", "de");// Schwerin
			handlePLZWeekly("66111", "de");// Saarbrücken
			handlePLZWeekly("28215", "de");// Bremen

			handlePLZToday("78467", "de");// Konstanz
			handlePLZToday("40213", "de");// Düsseldorf
			handlePLZToday("80331", "de");// München
			handlePLZToday("70173", "de");// Stuttgart
			handlePLZToday("30159", "de");// Hannover
			handlePLZToday("65183", "de");// Wiesbaden
			handlePLZToday("01069", "de");// Dresden
			handlePLZToday("55116", "de");// Mainz
			handlePLZToday("10785", "de");// Berlin
			handlePLZToday("24103", "de");// Kiel
			handlePLZToday("14467", "de");// Potsdam
			handlePLZToday("39104", "de");// Magdeburg
			handlePLZToday("99084", "de");// Erfurt
			handlePLZToday("20095", "de");// Hamburg
			handlePLZToday("19055", "de");// Schwerin
			handlePLZToday("66111", "de");// Saarbrücken
			handlePLZToday("28215", "de");// Bremen

			try {
				Thread.sleep(Integer.parseInt(System.getenv("APICallIntervall")));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Mqtt-Service: publishLiveWeatherData stopped");

	}

	@Async
	public void publishFakeWeatherData(WeatherData weatherData) {
		System.out.println("Mqtt-Service: publishFakeWeatherData started");
		this.fakeWeatherData = weatherData;
		transmittingGenerated = true;

		while (transmittingGenerated) {
			try {
				jsonInString = gson.toJson(this.fakeWeatherData);
				message = new MqttMessage(jsonInString.getBytes());
				System.out.println(jsonInString);
				client.publish("weekly", message);
				Thread.sleep(2000);
			} catch (MqttException | InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("*published");
		}
		System.out.println("Mqtt-Service: publishFakeWeatherData stopped");
	}

	private void handlePLZToday(String plz, String countryCode) {
		System.out.println("Mqtt-Service: handlePLZToday started, PLZ:" + plz);
		try {
			System.out.println("Reading API");
			String urlAPI = "http://api.openweathermap.org/data/2.5/find?q=" + plz + "," + countryCode + "&units=metric" + "&APPID=" + apiId;

			// Connect to the URL using java's native library
			URL url = new URL(urlAPI);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			// only for testing !!!
			System.out.println("TODAY: Reading request....");
			request.setReadTimeout(10000);

			request.connect();

			// Convert to a JSON object to print data

			JsonParser jp = new JsonParser(); // from gson
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); // Convert the input stream to a json element

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
			// System.out.println("WinDeg: " + winDeg.toString());

			if (winDeg == null) {

				result.setWindDeg(0.0);
			} else
				result.setWindDeg(winDeg.getAsDouble());

			jsonInString = gson.toJson(result);
			System.out.println(jsonInString);

			message = new MqttMessage(jsonInString.getBytes());

			client.publish(plz + "/today", message);
			System.out.println("Mqtt-Service: handlePLZToday published, PLZ:" + plz);

		} catch (IOException | MqttException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void handlePLZWeekly(String plz, String countryCode) {
		System.out.println("Mqtt-Service: handlePLZWeekly started, PLZ:" + plz);
		try {
			System.out.println("Reading API");
			String urlAPI = "http://api.openweathermap.org/data/2.5/forecast?zip=" + plz + "," + countryCode + "&units=metric" + "&APPID=" + apiId;

			// Connect to the URL using java's native library
			URL url = new URL(urlAPI);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			// only for testing !!!
			System.out.println("WEEKLY: Reading request....");
			request.setReadTimeout(10000);

			request.connect();

			// Convert to a JSON object to print data

			JsonParser jp = new JsonParser(); // from gson
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); // Convert the input stream to a json element

			JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.

			JsonArray jsonArray = rootobj.getAsJsonArray("list"); // Json request with all information

			JsonObject cityObject = rootobj.getAsJsonObject("city"); // Important information as JsonObject outside the array
			System.out.println(jsonArray);

			System.out.println("API reading complete");

			ArrayList<WeatherDataWeekly> result = new ArrayList<>();

			for (JsonElement daily : jsonArray) {

				WeatherDataWeekly dailyResult = new WeatherDataWeekly();

				dailyResult.setCityName(cityObject.getAsJsonObject().get("name").getAsString());
				dailyResult.setLatitude(cityObject.getAsJsonObject().get("coord").getAsJsonObject().get("lat").getAsDouble());
				dailyResult.setLongitude(cityObject.getAsJsonObject().get("coord").getAsJsonObject().get("lon").getAsDouble());
				dailyResult.setDate(daily.getAsJsonObject().get("dt_txt").getAsString());
				dailyResult.setTemperatureMax(daily.getAsJsonObject().get("main").getAsJsonObject().get("temp_max").getAsDouble());
				dailyResult.setTemperatureMin(daily.getAsJsonObject().get("main").getAsJsonObject().get("temp_min").getAsDouble());
				dailyResult.setHumitidy(daily.getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsInt());
				dailyResult.setPressure(daily.getAsJsonObject().get("main").getAsJsonObject().get("pressure").getAsInt());
				dailyResult.setCurrentWeather(daily.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());
				dailyResult.setCurrentWeatherId(daily.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt());
				dailyResult.setWeatherIcon(daily.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString());
				dailyResult.setTemperature(daily.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble());
				dailyResult.setWindspeed(daily.getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsDouble());
				dailyResult.setPlz(plz);

				JsonElement winDeg = daily.getAsJsonObject().get("wind").getAsJsonObject().get("deg");

				// Check if "Deg" Element is null
				if (winDeg == null) {

					dailyResult.setWindDeg(0.0);
				} else {
					dailyResult.setWindDeg(winDeg.getAsDouble());
				}
				result.add(dailyResult);
			}

			jsonInString = gson.toJson(result);
			System.out.println(jsonInString);

			message = new MqttMessage(jsonInString.getBytes());

			client.publish(plz + "/weekly", message);
			System.out.println("Mqtt-Service: handlePLZWeekly published, PLZ:" + plz);

		} catch (IOException | MqttException | NullPointerException e) {
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
