package cad.cep.model;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONMessage.
 */
public class JSONMessage implements IMessage{
	
	private String location;
	private Date timestamp;
	private int temperature;
	
	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	

	/* (non-Javadoc)
	 * @see cad.cep.model.IMessage#createMessage(byte[])
	 */
	@Override
	public IMessage createMessage(byte[] body) {
		Gson gson = new GsonBuilder().create();
		JSONMessage message = gson.fromJson(new String(body), this.getClass());
		this.copy(message);
		return this;
	}

	
	public String toString(){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}

	@Override
	public IMessage copy(IMessage toCopy) {
		JSONMessage copy = (JSONMessage) toCopy;
		this.setLocation(copy.getLocation());
		this.setTimestamp(this.getTimestamp());
		
		return this;
	}
}
