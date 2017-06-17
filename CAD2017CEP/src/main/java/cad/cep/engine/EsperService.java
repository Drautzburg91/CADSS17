package cad.cep.engine;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

import cad.cep.model.JSONMessage;

/**
 * The Class EsperService.
 */
public class EsperService {

	/** The enigne. */
	private EPServiceProvider enigne = EPServiceProviderManager.getDefaultProvider(); 

	/**
	 * Instantiates a new esper service.
	 */
	public EsperService(){
		enigne.getEPAdministrator().getConfiguration().addEventType(JSONMessage.class);
	}

	/**
	 * Register additional event. The most events are  registered by the factory.
	 *
	 * @param className the class name
	 * @return the esper service to add more events in a row
	 */
	public final EsperService registerAdditionalEvent(Class<?> className){
		enigne.getEPAdministrator().getConfiguration().addEventType(className);
		return this;
	}

	/**
	 * Creates the statement. The Statement is the pattern which the engine looks for. Most statements are added by the factoy
	 *
	 * @param statementQuery the statement query in SQL form
	 * @param listener the listener
	 * @return the EP statement which must be added in a different method
	 */
	public final EPStatement createStatement(String statementQuery, UpdateListener listener){
		EPStatement statement = createStatementWithoutListener(statementQuery);
		addListener(listener, statement);
		return statement;
	}
	
	/**
	 * Adds the listener. Adds the listener to the statement. Otherwise the pattern would not incoke any reaction.
	 *
	 * @param listener the listener
	 * @param statement the statement
	 */
	public final void addListener(UpdateListener listener, EPStatement statement) {
		statement.addListener(listener);
	}

	/**
	 * Creates the statement without listener. This method can be used to create context statements.
	 *
	 * @param statementQuery the statement query
	 * @return the EP statement
	 */
	public final EPStatement createStatementWithoutListener(String statementQuery) {
		return enigne.getEPAdministrator().createEPL(statementQuery);
	}

	/**
	 * Send event. Sends the Event to the EventStream.
	 *
	 * @param messageEvent the message event
	 * @return the esper service
	 */
	public EsperService sendEvent(JSONMessage messageEvent){
		if(messageEvent == null){
			throw new IllegalArgumentException("Message must not be null");
		}
		enigne.getEPRuntime().sendEvent(messageEvent);
		return this;	
	}
	
	/**
	 * Destroy connection.
	 */
	public void destroyConnection(){
		this.enigne.destroy();
	}

}
