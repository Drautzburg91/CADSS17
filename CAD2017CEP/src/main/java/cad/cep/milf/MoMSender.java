package cad.cep.milf;

import cad.cep.exceptions.MoMException;
import cad.cep.model.IMessage;
import cad.cep.mom.IMoM;
import cad.cep.mom.MomFactory;

/**
 * The Class MoMSender.
 */
public class MoMSender {

	private IMoM createMom;

	public MoMSender() {
		//not needed
	}

	public void setMom(IMoM mom){
		this.createMom = mom;
	}

	public void send(String topic, IMessage message) throws MoMException{
		if(createMom == null){
			createMom = MomFactory.createMom();
		}
		createMom.sendMessageToopic(topic, message);
	}
}
