package Mqtt;

public class WeatherData {

	double longitude;
	double latitude;
	String cityName;
	String plz;
	String weatherIcon;
	String currentWeather;
	int currentWeatherId;
	int pressure;
	int humitidy;
	double windspeed;
    double windDeg;
	double temperature;
	double temperatureMax;
	double temperatureMin;

	
	public String getWeatherIcon() {
		return weatherIcon;
	}
	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public int getCurrentWeatherId() {
		return currentWeatherId;
	}
	public void setCurrentWeatherId(int currentWeatherId) {
		this.currentWeatherId = currentWeatherId;
	}
	public double getWindDeg() {
		return windDeg;
	}
	public void setWindDeg(double windDeg) {
		this.windDeg = windDeg;
	}
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
	
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public int getHumitidy() {
		return humitidy;
	}
	public void setHumitidy(int humitidy) {
		this.humitidy = humitidy;
	}
	public double getWindspeed() {
		return windspeed;
	}
	public void setWindspeed(double windspeed) {
		this.windspeed = windspeed;
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
	public String getCurrentWeather() {
		return currentWeather;
	}
	public void setCurrentWeather(String currentWeather) {
		this.currentWeather = currentWeather;
	}

    @Override
    public String toString() {
        return "WeatherData{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", cityName='" + cityName + '\'' +
                ", plz='" + plz + '\'' +
                ", weatherIcon='" + weatherIcon + '\'' +
                ", currentWeather='" + currentWeather + '\'' +
                ", currentWeatherId=" + currentWeatherId +
                ", pressure=" + pressure +
                ", humitidy=" + humitidy +
                ", windspeed=" + windspeed +
                ", windDeg=" + windDeg +
                ", temperature=" + temperature +
                ", temperatureMax=" + temperatureMax +
                ", temperatureMin=" + temperatureMin +
                '}';
    }
}
