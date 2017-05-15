package cad.cep.engine;

import cad.cep.model.JSONMessage;

public class EngineControl {
	
	private static EngineControl instance = new EngineControl();
	private EsperService service;
	
	private EngineControl(){
		service = CEPFactory.createNewService();
	}
	
	public static EngineControl getInstance(){
		return instance;
	}
	
	public void sendEvent(JSONMessage event){
		service.sendEvent(event);
	}

}