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

	DatabaseStub stub = new DatabaseStub();
	public void send() throws MoMException{
		while(true){
			IMessage message = stub.readMessage("I don´t care");
			IMoM createMom = MomFactory.createMom();
			createMom.sendMessageToopic("aaa", message);
		}
	}
}
