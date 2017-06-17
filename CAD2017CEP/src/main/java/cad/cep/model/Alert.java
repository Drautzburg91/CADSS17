package cad.cep.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class Alert.
 */
public class Alert implements IMessage{
	
	/** The warning. */
	private String warning = "";
	
	/** The title. */
	private String title;
	
	/** The code. */
	private String code;
	
	/**
	 * Instantiates a new alert.
	 *
	 * @param title the title
	 * @param code the code
	 * @param warning the warning
	 */
	public Alert(String title, String code, String warning) {
		this.title = title;
		this.code = code;
		this.warning = warning;
		
	}

	/**
	 * Gets the warning.
	 *
	 * @return the warning
	 */
	public String getWarning() {
		return warning;
	}


	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/* (non-Javadoc)
	 * @see cad.cep.model.IMessage#createMessage(byte[], java.lang.String)
	 */
	@Override
	public IMessage createMessage(byte[] body, String topic) {
		Gson gson = new GsonBuilder().create();
		Alert fromJson = gson.fromJson(new String(body), this.getClass());
		return this.copy(fromJson);
	}

	/* (non-Javadoc)
	 * @see cad.cep.model.IMessage#copy(cad.cep.model.IMessage)
	 */
	@Override
	public IMessage copy(IMessage toCopy) {
		Alert alert = (Alert) toCopy;
		return new Alert(alert.getTitle(), alert.getCode(), alert.getWarning());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
}
