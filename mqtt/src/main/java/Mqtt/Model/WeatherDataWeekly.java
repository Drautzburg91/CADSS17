package Mqtt.Model;

public class WeatherDataWeekly {
	
	
	double longitude;
	double latitude;
	String plz;
	String weatherIcon;
	String currentWeather;
	String cityName;
	int currentWeatherId;
	int pressure;
	int humidity;
	double windspeed;
    double windDeg;
	double temperature;
	double temperatureMax;
	double temperatureMin;
	String date;
	
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public double getWindspeed() {
		return windspeed;
	}
	public void setWindspeed(double windspeed) {
		this.windspeed = windspeed;
	}
	public double getWindDeg() {
		return windDeg;
	}
	public void setWindDeg(double windDeg) {
		this.windDeg = windDeg;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getTemperatureMax() {
		return temperatureMax;
	}
	public void setTemperatureMax(double temperatureMax) {
		this.temperatureMax = temperatureMax;
	}
	public double getTemperatureMin() {
		return temperatureMin;
	}
	public void setTemperatureMin(double temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

}
