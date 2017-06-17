package cad.cep.model;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessage.
 */
public interface IMessage {

	/**
	 * Creates the message. This Method creates a new message object from a byte array,
	 *
	 * @param body the body as byte array
	 * @param topic the topic form which the message is send
	 * @return the new message object
	 */
	IMessage createMessage(byte[] body, String topic);
	
	/**
	 * Copy this message to another
	 *
	 * @param toCopy the message to copy
	 * @return the copied message
	 */
	IMessage copy(IMessage toCopy);
	
}
