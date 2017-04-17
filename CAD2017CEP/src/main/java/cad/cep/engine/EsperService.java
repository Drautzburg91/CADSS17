package cad.cep.engine;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

import cad.cep.model.JSONMessage;

public class EsperService {

	EPServiceProvider enigne = EPServiceProviderManager.getDefaultProvider(); 
	
	public EsperService(){
		enigne.getEPAdministrator().getConfiguration().addEventType(JSONMessage.class);
	}
	
	public final EsperService registerAdditionalEvent(Class<?> className){
		enigne.getEPAdministrator().getConfiguration().addEventType(className);
		return this;
	}
	
	public final EPStatement createStatement(String statementQuery, UpdateListener listener){
		EPStatement statement = createStatementWithoutListener(statementQuery);
		addListener(listener, statement);
		return statement;
	}

	public final void addListener(UpdateListener listener, EPStatement statement) {
		statement.addListener(listener);
	}

	public final EPStatement createStatementWithoutListener(String statementQuery) {
		return enigne.getEPAdministrator().createEPL(statementQuery);
	}
	
	public EsperService sendEvent(JSONMessage messageEvent){
	if(messageEvent == null){
		throw new IllegalArgumentException("Message must not be null");
	}
		enigne.getEPRuntime().sendEvent(messageEvent);
		return this;	
	}
	
}
