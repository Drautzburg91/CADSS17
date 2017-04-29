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
		//collects every Message and part them by location
		String context = "create context TemperatureByLocation partition by location from JSONMessage";
		//gets every locaition where the temperature drops under 0 within 2 hours
		String contextStatement = "context TemperatureByLocation select * from pattern["
				+ "every a=JSONMessage(temperature >=0) ->b=JSONMessage(temperature <0) where timer:within(120 minutes)]";
		service.createStatementWithoutListener(context);
		//reads a and b and prints the values
		service.createStatement(contextStatement, (newData, oldData)->{
			System.out.println("TEMPERATURE FALL");
			System.out.println(newData[0].get("a"));
			System.out.println(newData[0].get("b"));
		});
		JSONMessage message = new JSONMessage();
		message.setLocation("Radolfzell");
		message.setTemperature(17);
		service.sendEvent(message);
		service.sendEvent(messageEvent);
		
	}

}
