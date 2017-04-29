package cad.cep.milf;

import cad.cep.engine.CEPFactory;
import cad.cep.engine.EsperService;
import cad.cep.model.JSONMessage;

public class InformationLifeFlow {

	public static void main(String[] args) {
		EsperService service = CEPFactory.createNewService();
		JSONMessage messageEvent = new JSONMessage();
		messageEvent.setLocation("Radolfzell");
		messageEvent.setTemperature(-16);
		JSONMessage message = new JSONMessage();
		message.setLocation("Radolfzell");
		message.setTemperature(17);
		JSONMessage knMessage = new JSONMessage();
		knMessage.setLocation("Konstanz");
		knMessage.setTemperature(35);
		service.sendEvent(knMessage);
		service.sendEvent(message);
		service.sendEvent(messageEvent);
		JSONMessage newKN = new JSONMessage();
		newKN.setLocation("Konstanz");
		newKN.setTemperature(-100);
		service.sendEvent(newKN);
		
	}

}
