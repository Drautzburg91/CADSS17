package cad.cep.mom;

import cad.cep.exceptions.MoMException;
import cad.cep.model.IMessage;

/**
 * The Interface IMoM.
 */
public interface IMoM {

	/**
	 * Read message from topic.
	 *
	 * @param topic the topic to read from
	 * @return the message read from the topic
	 * @throws MoMException the MoM exception
	 */
	IMessage readMessageFromTopic(String topic) throws MoMException;
	
	/**
	 * Send message topic.
	 *
	 * @param queId the queid
	 * @param message the message
	 * @param retained the retained
	 * @throws MoMException the mo M exception
	 */
	void sendMessageTopic(String queId, IMessage message, boolean retained) throws MoMException;
	
	/**
	 * Close connection.
	 *
	 * @throws MoMException the MoM exception
	 */
	void closeConnection() throws MoMException;
	
	/**
	 * Re connect.
	 *
	 * @throws MoMException the MoM exception
	 */
	void reConnect() throws MoMException;
}
