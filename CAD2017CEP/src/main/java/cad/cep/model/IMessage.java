package cad.cep.model;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessage.
 */
public interface IMessage {

	/**
	 * Creates the message.
	 *
	 * @param body the body
	 * @return the i message
	 */
	IMessage createMessage(byte[] body, String topic);
	
	IMessage copy(IMessage toCopy);
	
	void addWarning(String warning);
}
