package cad.cep.engine;

import java.util.Map;

import cad.cep.model.JSONMessage;

public class CEPFactory {
	
	public static EsperService createNewService(){
		EsperService service = new EsperService();
		service.registerAdditionalEvent(JSONMessage.class);
		String statementQuery ="select location from JSONMessage";
		service.createStatement(statementQuery, (newData, oldData) ->{
		
				String location = (String) newData[0].get("location");
				System.out.println(location);
		});
		return service;
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
