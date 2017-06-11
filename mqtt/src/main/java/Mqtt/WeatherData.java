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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + ((currentWeather == null) ? 0 : currentWeather.hashCode());
		result = prime * result + currentWeatherId;
		result = prime * result + humitidy;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
		result = prime * result + pressure;
		temp = Double.doubleToLongBits(temperature);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(temperatureMax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(temperatureMin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((weatherIcon == null) ? 0 : weatherIcon.hashCode());
		temp = Double.doubleToLongBits(windDeg);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(windspeed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeatherData other = (WeatherData) obj;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		if (currentWeather == null) {
			if (other.currentWeather != null)
				return false;
		} else if (!currentWeather.equals(other.currentWeather))
			return false;
		if (currentWeatherId != other.currentWeatherId)
			return false;
		if (humitidy != other.humitidy)
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (plz == null) {
			if (other.plz != null)
				return false;
		} else if (!plz.equals(other.plz))
			return false;
		if (pressure != other.pressure)
			return false;
		if (Double.doubleToLongBits(temperature) != Double.doubleToLongBits(other.temperature))
			return false;
		if (Double.doubleToLongBits(temperatureMax) != Double.doubleToLongBits(other.temperatureMax))
			return false;
		if (Double.doubleToLongBits(temperatureMin) != Double.doubleToLongBits(other.temperatureMin))
			return false;
		if (weatherIcon == null) {
			if (other.weatherIcon != null)
				return false;
		} else if (!weatherIcon.equals(other.weatherIcon))
			return false;
		if (Double.doubleToLongBits(windDeg) != Double.doubleToLongBits(other.windDeg))
			return false;
		if (Double.doubleToLongBits(windspeed) != Double.doubleToLongBits(other.windspeed))
			return false;
		return true;
	}
}
