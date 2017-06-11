package cad.cep.model;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeeklyForcast implements IMessage{
	
	private Set<Day> days = new HashSet<>();
	
	private String plz;
	
	public String getPlz(){
		return this.plz;
	}

	@Override
	public IMessage createMessage(byte[] body, String topic) {
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(new String(body));
			System.out.println(element);
			JsonArray array = element.getAsJsonArray();
			for (JsonElement jsonElement : array) {
				Day day = new Day();
				JsonElement city = jsonElement.getAsJsonObject().get("cityName");
				JsonElement weatherIcon = jsonElement.getAsJsonObject().get("weatherIcon");
				JsonElement currentWeather = jsonElement.getAsJsonObject().get("currentWeather");
				JsonElement date = jsonElement.getAsJsonObject().get("date");
				JsonElement plz = jsonElement.getAsJsonObject().get("plz");
				JsonElement id = jsonElement.getAsJsonObject().get("currentWeatherId");
				JsonElement temperature = jsonElement.getAsJsonObject().get("temperature");
				day.setCityName(city.getAsString());
				day.setCurrentWeatherId(id.getAsInt());
				day.setWeatherIcon(weatherIcon.getAsString());
				day.setDate(date.getAsString());
				day.setPlz(plz.getAsString());
				this.plz = plz.getAsString();
				day.setTemperature(temperature.getAsInt());
				day.setCurrentWeather(currentWeather.getAsString());
				days.add(day);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IMessage copy(IMessage toCopy) {
		//FOrcast should not be copied
		return toCopy;
	}
	
	public String toString(){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	

}
