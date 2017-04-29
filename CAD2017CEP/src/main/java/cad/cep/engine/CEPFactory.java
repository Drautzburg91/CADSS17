package cad.cep.engine;

import java.util.Map;

import cad.cep.database.DatabaseStub;
import cad.cep.model.JSONMessage;

public class CEPFactory {
	
	public static EsperService createNewService(){
		EsperService service = new EsperService();
		service.registerAdditionalEvent(JSONMessage.class);
		String statementQuery ="select * from JSONMessage";
		service.createStatement(statementQuery, (newData, oldData) ->{
			DatabaseStub stub = new DatabaseStub();
			stub.saveMessage((JSONMessage)newData[0].getUnderlying());
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
