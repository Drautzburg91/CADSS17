package cad.cep.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class JSONMessage.
 */
public class JSONMessage implements IMessage{
	
	/** The longitude. */
	private double longitude;
	
	/** The latitude. */
	private double latitude;
	
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
	
	/** The humidity. */
	private int humidity;
	
	/** The pressure. */
	private int pressure;
	
	/** The windspeed. */
	private double windspeed;
	
	/** The wind deg. */
	private double windDeg;
	
	/** The temperature. */
	private double temperature;
	
	/** The temperature max. */
	private double temperatureMax;
	
	/** The temperature min. */
	private double temperatureMin;
	
	/** The topic. */
	private String topic;
	
	
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
	 * Gets the topic.
	 *
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
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
	 * Sets the topic.
	 *
	 * @param topic the new topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}



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
	 * Gets the temperature max.
	 *
	 * @return the temperature max
	 */
	public double getTemperatureMax() {
		return temperatureMax;
	}

	/**
	 * Sets the temperature max.
	 *
	 * @param temperatureMax the new temperature max
	 */
	public void setTemperatureMax(double temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	/**
	 * Gets the temperature min.
	 *
	 * @return the temperature min
	 */
	public double getTemperatureMin() {
		return temperatureMin;
	}

	/**
	 * Sets the temperature min.
	 *
	 * @param temperatureMin the new temperature min
	 */
	public void setTemperatureMin(double temperatureMin) {
		this.temperatureMin = temperatureMin;
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
	 * Gets the wind deg.
	 *
	 * @return the wind deg
	 */
	public double getWindDeg() {
		return windDeg;
	}

	/**
	 * Sets the wind deg.
	 *
	 * @param windDeg the new wind deg
	 */
	public void setWindDeg(double windDeg) {
		this.windDeg = windDeg;
	}

	/**
	 * Gets the windspeed.
	 *
	 * @return the windspeed
	 */
	public double getWindspeed() {
		return windspeed;
	}

	/**
	 * Sets the windspeed.
	 *
	 * @param windspeed the new windspeed
	 */
	public void setWindspeed(double windspeed) {
		this.windspeed = windspeed;
	}

	/**
	 * Gets the humidity.
	 *
	 * @return the humidity
	 */
	public int getHumidity() {
		return humidity;
	}

	/**
	 * Sets the humidity.
	 *
	 * @param humilidy the new humidity
	 */
	public void setHumidity(int humilidy) {
		this.humidity = humilidy;
	}

	/**
	 * Gets the pressure.
	 *
	 * @return the pressure
	 */
	public int getPressure() {
		return pressure;
	}

	/**
	 * Sets the pressure.
	 *
	 * @param pressure the new pressure
	 */
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	
	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}



	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}



	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
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
		System.out.println("######################################################");
		return this.copy(fromJson);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}

	/* (non-Javadoc)
	 * @see cad.cep.model.IMessage#copy(cad.cep.model.IMessage)
	 */
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
