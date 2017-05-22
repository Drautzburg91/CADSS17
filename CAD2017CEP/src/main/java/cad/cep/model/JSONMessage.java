package cad.cep.model;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONMessage.
 */
public class JSONMessage implements IMessage{
	
	//TODO cityname
	private String cityName;
	private Date timestamp;
	private int temperature;
	private int temperatureMax;
	private int temperatureMin;
	private String currentWeather;
	private String currentWeatherId;
	private double windDeg;
	private double windspeed;
	private int humilidy;
	private int pressure;
	String warining ="";
	
	public String getWarining() {
		return warining;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String location) {
		this.cityName = location;
	}
	
	public String getCurrentWeatherId() {
		return currentWeatherId;
	}

	public void setCurrentWeatherId(String currentWeatherId) {
		this.currentWeatherId = currentWeatherId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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

	public int getHumilidy() {
		return humilidy;
	}

	public void setHumilidy(int humilidy) {
		this.humilidy = humilidy;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	/* (non-Javadoc)
	 * @see cad.cep.model.IMessage#createMessage(byte[])
	 */
	@Override
	public IMessage createMessage(byte[] body) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(new String(body), this.getClass());
	}
	
	public void addWarning(String warining){
		this.warining = this.warining + warining;
	}

	
	public String toString(){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}

	@Override
	public IMessage copy(IMessage toCopy) {
		JSONMessage copy = (JSONMessage) toCopy;
		this.setCityName(copy.getCityName());
		this.setTimestamp(this.getTimestamp());
		
		return this;
	}
}
