package cad.cep.milf.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cad.cep.model.Day;

public class AVGWeather {

	private static Map<String, List<Day>> days = new HashMap<>();

	public static void addEntryToDay(Day day){
		List<Day> dayList = days.get(day.getPlz());
		if(dayList == null){
			dayList = new ArrayList<>();
		}
		dayList.add(day);
		days.put(day.getPlz(), dayList);
	}
	public static Day getCalculatetCast(String key, String Date){
		return calculate(key, Date);
	}
	private static Day calculate(String key, String date) {
		List<Day> currentDays = days.get(key);
		int minTemp = Integer.MAX_VALUE;
		int maxTemp = Integer.MIN_VALUE;
		List<WeatherWrapper> wrappers = new ArrayList<>();
		for (Day day : currentDays) {
			if(!day.getDate().equals(date)){
				continue;
			}
			if(day.getMinTemperature() < minTemp){
				minTemp = day.getMinTemperature();
			}
			if(day.getMaxTemperature() > maxTemp){
				maxTemp = day.getMaxTemperature();
			}
			WeatherWrapper wrapper = new WeatherWrapper();
			getWeather(wrappers, day, wrapper);
		}
		WeatherWrapper mostUsed = getMostUsed(wrappers);
		Day finalDay = new Day();
		finalDay.setMinTemperature(minTemp);
		finalDay.setMaxTemperature(maxTemp);
		finalDay.setTemperature(minTemp);
		finalDay.setCurrentWeather(mostUsed.getDescription());
		finalDay.setCurrentWeatherId(mostUsed.getId());
		finalDay.setWeatherIcon(mostUsed.getIcon());
		Day firstDay = currentDays.get(0);
		try {
			finalDay.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		finalDay.setPlz(firstDay.getPlz());
		finalDay.setCityName(firstDay.getCityName());
		return finalDay;
	}
	private static WeatherWrapper getMostUsed(List<WeatherWrapper> wrappers) {
		WeatherWrapper mostUsed = wrappers.get(0);
		for (WeatherWrapper weatherWrapper : wrappers) {
			if(mostUsed.getCounter()<weatherWrapper.getCounter()){
				mostUsed = weatherWrapper;
			}
		}
		return mostUsed;
	}
	private static void getWeather(List<WeatherWrapper> wrappers, Day day, WeatherWrapper wrapper) {
		wrapper.setId(day.getCurrentWeatherId());
		wrapper.setDescription(day.getCurrentWeather());
		wrapper.setIcon(day.getWeatherIcon());
		if(wrappers.contains(wrapper)){
			wrappers.get(wrappers.indexOf(wrapper)).count();
		}else{
			wrappers.add(wrapper);
		}
	}

	public static class WeatherWrapper{
		private int id;

		private String icon;

		private String description;

		private int counter = 0;

		public void count(){
			counter++;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		public void setId(int id){
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public String getIcon() {
			return icon;
		}

		public String getDescription() {
			return description;
		}

		public int getCounter() {
			return counter;
		}

		public boolean equals(Object o){
			if(o instanceof WeatherWrapper){
				WeatherWrapper wrapper = (WeatherWrapper) o;
				return wrapper.getId() == (this.getId());
			}
			return false;
		}

	}
}
