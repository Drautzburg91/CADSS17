package cad.cep.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Alert implements IMessage{
	
	private String warning = "";
	
	private String title;
	
	private String code;
	
	public Alert(String title, String code, String warning) {
		this.title = title;
		this.code = code;
		this.warning = warning;
		
	}

	public String getWarning() {
		return warning;
	}


	public String getTitle() {
		return title;
	}


	public String getCode() {
		return code;
	}


	@Override
	public IMessage createMessage(byte[] body, String topic) {
		Gson gson = new GsonBuilder().create();
		Alert fromJson = gson.fromJson(new String(body), this.getClass());
		return this.copy(fromJson);
	}

	@Override
	public IMessage copy(IMessage toCopy) {
		Alert alert = (Alert) toCopy;
		return new Alert(alert.getTitle(), alert.getCode(), alert.getWarning());
	}

	public String toString(){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
}
