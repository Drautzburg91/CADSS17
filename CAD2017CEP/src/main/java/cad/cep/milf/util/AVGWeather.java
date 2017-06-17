package cad.cep.milf.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cad.cep.model.Day;

/**
 * The Class AVGWeather.
 */
public class AVGWeather {

	/** The days. */
	private static Map<String, List<Day>> days = new HashMap<>();

	/**
	 * Adds the entry to day.
	 *
	 * @param day the day to add
	 */
	public static void addEntryToDay(Day day){
		List<Day> dayList = days.get(day.getPlz());
		if(dayList == null){
			dayList = new ArrayList<>();
		}
		dayList.add(day);
		days.put(day.getPlz(), dayList);
	}
	
	/**
	 * Gets the calculated cast. It iterates over the added days and looks for every day registered under this PLZ 
	 * and checks if it is at the same day. The calculated cast uses the overall min and may temperature and the average weather for that day.
	 *
	 * @param key the key the PLZ of the city
	 * @param Date the date on which day 
	 * @return the calculated cast
	 */
	public static Day getCalculatedCast(String key, String Date){
		return calculate(key, Date);
	}
	
	/**
	 * Calculate. This method searches for MIN and MAX temperatue and the average weather.
	 *
	 * @param key the key the plz
	 * @param date the date
	 * @return the day
	 */
	private static Day calculate(String key, String date) {
		List<Day> currentDays = days.get(key);
		double minTemp = Integer.MAX_VALUE;
		double maxTemp = Integer.MIN_VALUE;
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
	
	/**
	 * Gets the most used weather. 
	 *
	 * @param wrappers the wrappers
	 * @return the most used
	 */
	private static WeatherWrapper getMostUsed(List<WeatherWrapper> wrappers) {
		WeatherWrapper mostUsed = wrappers.get(0);
		for (WeatherWrapper weatherWrapper : wrappers) {
			if(mostUsed.getCounter()<weatherWrapper.getCounter()){
				mostUsed = weatherWrapper;
			}
		}
		return mostUsed;
	}
	
	/**
	 * Gets the weather.
	 *
	 * @param wrappers the wrappers
	 * @param day the day
	 * @param wrapper the wrapper
	 * @return the weather
	 */
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

	/**
	 * The Class WeatherWrapper. This class is used to keep every information. 
	 */
	public static class WeatherWrapper{
		
		/** The id. */
		private int id;

		/** The icon. */
		private String icon;

		/** The description. */
		private String description;

		/** The counter. */
		private int counter = 0;

		/**
		 * Count.
		 */
		public void count(){
			counter++;
		}

		/**
		 * Sets the icon.
		 *
		 * @param icon the new icon
		 */
		public void setIcon(String icon) {
			this.icon = icon;
		}

		/**
		 * Sets the description.
		 *
		 * @param description the new description
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
		/**
		 * Sets the id.
		 *
		 * @param id the new id
		 */
		public void setId(int id){
			this.id = id;
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * Gets the icon.
		 *
		 * @return the icon
		 */
		public String getIcon() {
			return icon;
		}

		/**
		 * Gets the description.
		 *
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * Gets the counter.
		 *
		 * @return the counter
		 */
		public int getCounter() {
			return counter;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object o){
			if(o instanceof WeatherWrapper){
				WeatherWrapper wrapper = (WeatherWrapper) o;
				return wrapper.getId() == (this.getId());
			}
			return false;
		}

	}
}
