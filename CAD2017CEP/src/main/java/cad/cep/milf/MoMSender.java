package cad.cep.milf;

import cad.cep.database.DatabaseStub;
import cad.cep.exceptions.MoMException;
import cad.cep.model.IMessage;
import cad.cep.mom.IMoM;
import cad.cep.mom.MomFactory;

/**
 * The Class MoMSender.
 */
public class MoMSender {

	public void send(String topic, IMessage message) throws MoMException{
			IMoM createMom = MomFactory.createMom("BackEndSender");
			createMom.sendMessageToopic(topic, message);
	}
}
