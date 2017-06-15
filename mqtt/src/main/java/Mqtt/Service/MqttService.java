package Mqtt.Service;

import Mqtt.Model.WeatherData;
import Mqtt.Model.WeatherDataWeekly;
import com.google.gson.*;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	private HashMap<String, String> cities;

	public MqttService() {
		System.out.println("MqttService started: "+System.getenv("CadRabbit_Host"));
		// credentials have to be stored in env variables
		options = new MqttConnectOptions();
        System.out.println("Connect-Options:" +System.getenv("CadRabbit_UserName"));
		options.setUserName(System.getenv("CadRabbit_UserName"));
		options.setPassword(System.getenv("CadRabbit_Password").toCharArray());
        System.out.println("Host: " + System.getenv("CadRabbit_Host"));
		gson = new GsonBuilder().setPrettyPrinting().create();
		initPlz();
        System.out.println("Host: " + System.getenv("CadRabbit_Host"));
		try {
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
            System.out.println("MqttException:" +e);
            e.printStackTrace();
		}
	}

	@Async
	public void publishLiveWeatherData() {
		System.out.println("Mqtt-Service: publishLiveWeatherData started");
		transmittingLive = true;
		while (transmittingLive) {
			for (Map.Entry<String, String> city : cities.entrySet()) {
				String citiesKey[] = city.getKey().split("-");
				String countryCode = citiesKey[0];
				String plz = citiesKey[1];
				handlePLZToday(plz, countryCode);
				handlePLZWeekly(plz, countryCode);
			}

			try {
				String apiCallIntervall = System.getenv("APICallIntervall");
				if (apiCallIntervall != null) {
					Thread.sleep(Integer.parseInt(apiCallIntervall));
				} else {
					Thread.sleep(60000);
				}

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
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MqttPersistenceException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				reconnectToMoM();
				e.printStackTrace();
			}
			System.out.println("*published");
		}
		System.out.println("Mqtt-Service: publishFakeWeatherData stopped");
	}

	public static WeatherData dailyToWeatherData(JsonElement root, String plz) {

		JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.

		JsonArray jsonArray = rootobj.getAsJsonArray("list"); // Json request with all information

		// System.out.println(jsonArray);

		System.out.println("API reading complete");

		WeatherData result = new WeatherData();

		result.setCityName(jsonArray.get(0).getAsJsonObject().get("name").getAsString());
		result.setLongitude(jsonArray.get(0).getAsJsonObject().get("coord").getAsJsonObject().get("lon").getAsDouble());
		result.setLatitude(jsonArray.get(0).getAsJsonObject().get("coord").getAsJsonObject().get("lat").getAsDouble());
		result.setHumidity(jsonArray.get(0).getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsInt());
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

		return result;
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

			WeatherData result = dailyToWeatherData(root, plz);

			jsonInString = gson.toJson(result);
			System.out.println(jsonInString);

			message = new MqttMessage(jsonInString.getBytes());

			client.publish(plz + "/today", message);
			System.out.println("Mqtt-Service: handlePLZToday published, PLZ:" + plz);

		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			reconnectToMoM();
			e.printStackTrace();
		}
	}

	public static ArrayList<WeatherDataWeekly> weeklyToWeatherDataWeekly(JsonElement root, String plz) {

		JsonObject rootobj = root.getAsJsonObject(); // May be an array, may be an object.

		JsonArray jsonArray = rootobj.getAsJsonArray("list"); // Json request with all information

		JsonObject cityObject = rootobj.getAsJsonObject("city"); // Important information as JsonObject outside the array
		// System.out.println(jsonArray);

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
			dailyResult.setHumidity(daily.getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsInt());
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

		return result;
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

			ArrayList<WeatherDataWeekly> result = weeklyToWeatherDataWeekly(root, plz);

			jsonInString = gson.toJson(result);
			System.out.println(jsonInString);

			message = new MqttMessage(jsonInString.getBytes());

			client.publish(plz + "/weekly", message);
			System.out.println("Mqtt-Service: handlePLZWeekly published, PLZ:" + plz);



		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			reconnectToMoM();
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

	private void initPlz() {
		cities = new HashMap<>();
		cities.put("de-78467", "Konstanz");
		cities.put("de-40213", "Düsseldorf");
		cities.put("de-80331", "München");
		cities.put("de-70173", "Stuttgart");
		cities.put("de-30159", "Hannover");
		cities.put("de-65183", "Wiesbaden");
		cities.put("de-01069", "Dresden");
		cities.put("de-55116", "Mainz");
		cities.put("de-10785", "Berlin");
		cities.put("de-24103", "Kiel");
		cities.put("de-14467", "Potsdam");
		cities.put("de-39104", "Magdeburg");
		cities.put("de-99084", "Erfurt");
		cities.put("de-20095", "Hamburg");
		cities.put("de-19055", "Schwerin");
		cities.put("de-66111", "Saarbrücken");
		cities.put("de-28215", "Bremen");
	}

	private void reconnectToMoM(){
		try {
			Thread.sleep(1000);
			client.connect(options);
		} catch (MqttException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
