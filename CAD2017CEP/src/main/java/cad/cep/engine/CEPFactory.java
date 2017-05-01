package cad.cep.engine;

import cad.cep.database.DatabaseStub;
import cad.cep.model.JSONMessage;

public class CEPFactory {
	
	public static EsperService createNewService(){
		EsperService service = new EsperService();
		service.registerAdditionalEvent(JSONMessage.class);
		addUpdateEvent(service);
		addWarning(service);
		addContext(service);
		return service;
	}
	private static void addUpdateEvent(EsperService service) {
		String statementQuery ="select * from JSONMessage";
		service.createStatement(statementQuery, (newData, oldData) ->{
			for (int i = 0; i < oldData.length; i++) {
				DatabaseStub stub = new DatabaseStub();
				stub.saveMessage((JSONMessage)newData[0].getUnderlying());
			}
		});
	}
	private static void addWarning(EsperService service) {
		String statement = "select temperature, location from JSONMessage"
				+" where temperature <= 0";
		service.createStatement(statement, (newData, oldData)->{
			for (int i = 0; i < newData.length; i++) {
				System.out.println("TEMPERATURE WARNING:");
				System.out.println(newData[i].get("temperature"));
				System.out.println("in " + newData[i].get("location"));

			}
		});
	}
	private static void addContext(EsperService service) {
		//collects every Message and part them by location
		String context = "create context TemperatureByLocation partition by location from JSONMessage";
		//gets every locaition where the temperature drops under 0 within 2 hours
		String contextStatement = "context TemperatureByLocation select * from pattern["
				+ "every a=JSONMessage(temperature >=0) ->b=JSONMessage(temperature <0) where timer:within(120 minutes)]";
		service.createStatementWithoutListener(context);
		//reads a and b and prints the values
		service.createStatement(contextStatement, (newData, oldData)->{
			for (int i = 0; i < newData.length; i++) {
				System.out.println("TEMPERATURE FALL");
				System.out.println(newData[i].get("a"));
				System.out.println(newData[i].get("b"));
			}
		});
	}
	//every other part is like a sql statement
	public static String createWindow(boolean timeBased, String value){
		if(timeBased){
			return String.format(":time(%s)", value);
		}else{
			return String.format(":length(%s)", value);
		}
	}

}
