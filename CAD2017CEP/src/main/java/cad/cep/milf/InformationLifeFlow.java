package cad.cep.milf;

import cad.cep.engine.CEPFactory;
import cad.cep.engine.EsperService;
import cad.cep.model.JSONMessage;

public class InformationLifeFlow {

	public static void main(String[] args) {
		EsperService service = CEPFactory.createNewService();
		JSONMessage messageEvent = new JSONMessage();
		String statement = "select temperature, location from JSONMessage"
		+" where temperature <= 0";
		System.out.println(statement);
		service.createStatement(statement, (newData, oldData)->{
			System.out.println("TEMPERATURE WARNING:");
			System.out.println(newData[0].get("temperature"));
			System.out.println("in " + newData[0].get("location"));
		});
		messageEvent.setLocation("Radolfzell");
		messageEvent.setTemperature(-16);
		service.sendEvent(messageEvent);
		
	}

}
