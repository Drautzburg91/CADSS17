package cad.cep.milf;

import cad.cep.exceptions.MoMException;
import cad.cep.model.IMessage;
import cad.cep.mom.IMoM;
import cad.cep.mom.MomFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class MoMSender.
 */
public class MoMSender {

	/** The create mom. */
	private IMoM createMom;

	/**
	 * Instantiates a new MoM sender.
	 */
	public MoMSender() {
		//not needed
	}

	/**
	 * Sets the mom.
	 *
	 * @param mom the new mom
	 */
	public void setMom(IMoM mom){
		this.createMom = mom;
	}

	/**
	 * Send. Sends the message to the MoM
	 *
	 * @param topic the topic which should be used
	 * @param message the message for the client
	 * @param retained the retained. Should be true if the message should stay until a new one is sended.
	 * @throws MoMException the MoM exception
	 */
	public void send(String topic, IMessage message, boolean retained) throws MoMException{
		if(createMom == null){
			createMom = MomFactory.createOrLoadMom();
		}
		createMom.sendMessageTopic(topic, message, retained);
	}
}
