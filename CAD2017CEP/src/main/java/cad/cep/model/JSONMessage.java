package cad.cep.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONMessage.
 */
public class JSONMessage implements IMessage{
	
	private double longitude;
	private double latitude;
	private String cityName;
	private String plz;
	private String weatherIcon;
	private String currentWeather;
	private int currentWeatherId;
	private int humidity;
	private int pressure;
	private double windspeed;
	private double windDeg;
	private int temperature;
	private int temperatureMax;
	private int temperatureMin;
	private String topic;
	
	
	public String getCityName() {
		return cityName;
	}



	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



	public String getWeatherIcon() {
		return weatherIcon;
	}

	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}



	public String getTopic() {
		return topic;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}



	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getCurrentWeatherId() {
		return currentWeatherId;
	}

	public void setCurrentWeatherId(int currentWeatherId) {
		this.currentWeatherId = currentWeatherId;
	}

	public int getTemperatureMax() {
		return temperatureMax;
	}

	public void setTemperatureMax(int temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public int getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(int temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public String getCurrentWeather() {
		return currentWeather;
	}

	public void setCurrentWeather(String currentWeather) {
		this.currentWeather = currentWeather;
	}

	public double getWindDeg() {
		return windDeg;
	}

	public void setWindDeg(double windDeg) {
		this.windDeg = windDeg;
	}

	public double getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(double windspeed) {
		this.windspeed = windspeed;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humilidy) {
		this.humidity = humilidy;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
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



	/* (non-Javadoc)
	 * @see cad.cep.model.IMessage#createMessage(byte[])
	 */
	@Override
	public IMessage createMessage(byte[] body, String topic) {
		Gson gson = new GsonBuilder().create();
		JSONMessage fromJson = gson.fromJson(new String(body), this.getClass());
		fromJson.setTopic(topic);
		return this.copy(fromJson);
	}
	
	public String toString(){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}

	@Override
	public IMessage copy(IMessage toCopy) {
		JSONMessage copy = (JSONMessage) toCopy;
		this.setCityName(copy.getCityName());
//		this.setTimestamp(this.getTimestamp());
		this.setCurrentWeather(copy.getCurrentWeather());
		this.setCurrentWeatherId(copy.getCurrentWeatherId());
		this.setHumidity(copy.getHumidity());
		this.setPlz(copy.getPlz());
		this.setTemperature(copy.getTemperature());
		this.setPressure(copy.getPressure());
		this.setTemperatureMax(copy.getTemperatureMax());
		this.setTemperatureMin(copy.getTemperatureMin());
		this.setWeatherIcon(copy.getWeatherIcon());
		this.setWindDeg(copy.getWindDeg());
		this.setWindspeed(copy.getWindspeed());
		this.setLongitude(copy.getLongitude());
		this.setLatitude(copy.getLatitude());
		this.setTopic(copy.getTopic());
		
		return this;
	}
}
