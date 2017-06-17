package cad.cep.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The Class Day.
 */
public class Day implements Comparable<Day>{

	/** The temperature. */
	private double temperature;
	
	/** The city name. */
	private String cityName;
	
	/** The plz. */
	private String plz;
	
	/** The weather icon. */
	private String weatherIcon;
	
	/** The current weather. */
	private String currentWeather;
	
	/** The current weather id. */
	private int currentWeatherId;
	
	/** The date. */
	private String date;
	
	/** The temperature max. */
	private double temperatureMax;
	
	/** The temperature min. */
	private double temperatureMin;
	
	/** The Constant FROMAT. */
	private static final SimpleDateFormat FROMAT = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Gets the temperature.
	 *
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * Sets the temperature.
	 *
	 * @param temperature the new temperature
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * Gets the city name.
	 *
	 * @return the city name
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * Sets the city name.
	 *
	 * @param cityName the new city name
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * Gets the plz.
	 *
	 * @return the plz
	 */
	public String getPlz() {
		return plz;
	}

	/**
	 * Sets the plz.
	 *
	 * @param plz the new plz
	 */
	public void setPlz(String plz) {
		this.plz = plz;
	}

	/**
	 * Gets the weather icon.
	 *
	 * @return the weather icon
	 */
	public String getWeatherIcon() {
		return weatherIcon;
	}

	/**
	 * Sets the weather icon.
	 *
	 * @param weatherIcon the new weather icon
	 */
	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	/**
	 * Gets the current weather.
	 *
	 * @return the current weather
	 */
	public String getCurrentWeather() {
		return currentWeather;
	}

	/**
	 * Sets the current weather.
	 *
	 * @param currentWeather the new current weather
	 */
	public void setCurrentWeather(String currentWeather) {
		this.currentWeather = currentWeather;
	}

	/**
	 * Gets the current weather id.
	 *
	 * @return the current weather id
	 */
	public int getCurrentWeatherId() {
		return currentWeatherId;
	}

	/**
	 * Sets the current weather id.
	 *
	 * @param currentWeatherId the new current weather id
	 */
	public void setCurrentWeatherId(int currentWeatherId) {
		this.currentWeatherId = currentWeatherId;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 * @throws ParseException the parse exception
	 */
	public void setDate(String date) throws ParseException {
		this.date = FROMAT.format(FROMAT.parse(date));
	}

	/**
	 * Gets the max temperature.
	 *
	 * @return the max temperature
	 */
	public double getMaxTemperature() {
		return temperatureMax;
	}

	/**
	 * Sets the max temperature.
	 *
	 * @param maxTemperature the new max temperature
	 */
	public void setMaxTemperature(double maxTemperature) {
		this.temperatureMax = maxTemperature;
	}

	/**
	 * Gets the min temperature.
	 *
	 * @return the min temperature
	 */
	public double getMinTemperature() {
		return temperatureMin;
	}

	/**
	 * Sets the min temperature.
	 *
	 * @param minTemperature the new min temperature
	 */
	public void setMinTemperature(double minTemperature) {
		this.temperatureMin = minTemperature;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (date.hashCode());
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Day){
			Day day = (Day) obj;
			if(day.getPlz().equals(this.plz) && day.getDate().equals(this.date)){
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Day o) {
		return this.date.compareTo(o.getDate());
	}
	
}
