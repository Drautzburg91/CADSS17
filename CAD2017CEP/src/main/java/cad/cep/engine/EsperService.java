package cad.cep.engine;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

import cad.cep.model.JSONMessage;

public class EsperService {

	EPServiceProvider enigne = EPServiceProviderManager.getDefaultProvider(); 
	protected EsperService(){/*not needed */}
	
	protected final EsperService registerEvents(){
		enigne.getEPAdministrator().getConfiguration().addEventType(JSONMessage.class);
		return this;
	}
	
	protected final EPStatement createStatement(String statementQuery, UpdateListener listener){
		EPStatement statement = enigne.getEPAdministrator().createEPL(statementQuery);
		statement.addListener(listener);
		return statement;
	}
	
	public EsperService sendEvent(JSONMessage messageEvent){
	if(messageEvent == null){
		throw new IllegalArgumentException("Message must not be null");
	}
		enigne.getEPRuntime().sendEvent(messageEvent);
		return this;	
	}
	
}
