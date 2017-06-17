package cad.cep.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import cad.cep.milf.util.AVGWeather;

/**
 * The Class WeeklyForcast.
 */
public class WeeklyForcast implements IMessage{
	
	/** The days. */
	private Set<Day> days = new LinkedHashSet<>();
	
	/** The plz. */
	private String plz;
	
	/**
	 * Gets the plz.
	 *
	 * @return the plz
	 */
	public String getPlz(){
		return this.plz;
	}

	/* (non-Javadoc)
	 * @see cad.cep.model.IMessage#createMessage(byte[], java.lang.String)
	 */
	@Override
	public IMessage createMessage(byte[] body, String topic) {
		try {
			JsonParser parser = new JsonParser();
		
			System.out.println("BODY:"+	new String(body));
			if(topic.contains("today")){}else if(topic.contains("weekly")){
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
				JsonElement maxTemp = jsonElement.getAsJsonObject().get("temperatureMax");
				JsonElement min = jsonElement.getAsJsonObject().get("temperatureMin");
				
				day.setCityName(city.getAsString());
				day.setCurrentWeatherId(id.getAsInt());
				day.setWeatherIcon(weatherIcon.getAsString());
				day.setDate(date.getAsString());
				day.setPlz(plz.getAsString());
				this.plz = plz.getAsString();
				day.setTemperature(temperature.getAsDouble());
				day.setCurrentWeather(currentWeather.getAsString());
				day.setMaxTemperature(maxTemp.getAsDouble());
				day.setMinTemperature(min.getAsDouble());
				AVGWeather.addEntryToDay(day);
				days.add(day);
				}
			}
		} catch (Exception e) {
			System.out.println("Das ist der Fehler" + e);
		}
		Set<Day> averageDays = new LinkedHashSet<>();
		for (Day day : days) {
			averageDays.add(AVGWeather.getCalculatedCast(day.getPlz(), day.getDate()));
		}
		days = averageDays;
		return this;
	}

	/* (non-Javadoc)
	 * @see cad.cep.model.IMessage#copy(cad.cep.model.IMessage)
	 */
	@Override
	public IMessage copy(IMessage toCopy) {
		//FOrcast should not be copied
		return toCopy;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	

}
