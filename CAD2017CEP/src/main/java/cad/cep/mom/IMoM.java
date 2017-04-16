package cad.cep.mom;

import cad.cep.exceptions.MoMException;
import cad.cep.model.IMessage;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMoM.
 */
public interface IMoM {

	/**
	 * Read message from topic.
	 *
	 * @param topic the topic
	 * @return the i message
	 * @throws MoMException the mo M exception
	 */
	IMessage readMessageFromTopic(String topic) throws MoMException;
	
	/**
	 * Send message toopic.
	 *
	 * @param queId the que id
	 * @param message the message
	 * @throws MoMException the mo M exception
	 */
	void sendMessageToopic(String queId, IMessage message) throws MoMException;
	
	/**
	 * Close connection.
	 *
	 * @throws MoMException the mo M exception
	 */
	void closeConnection() throws MoMException;
}
