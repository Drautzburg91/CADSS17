package cad.cep.model;

import java.text.Normalizer.Form;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Day{

	private int temperature;
	private String cityName;
	private String plz;
	private String weatherIcon;
	private String currentWeather;
	private int currentWeatherId;
	private String date;
	
	private static final SimpleDateFormat FROMAT = new SimpleDateFormat("yyyy-MM-dd");

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getWeatherIcon() {
		return weatherIcon;
	}

	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	public String getCurrentWeather() {
		return currentWeather;
	}

	public void setCurrentWeather(String currentWeather) {
		this.currentWeather = currentWeather;
	}

	public int getCurrentWeatherId() {
		return currentWeatherId;
	}

	public void setCurrentWeatherId(int currentWeatherId) {
		this.currentWeatherId = currentWeatherId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) throws ParseException {
		this.date = FROMAT.format(FROMAT.parse(date));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
		return result;
	}

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
	
}
