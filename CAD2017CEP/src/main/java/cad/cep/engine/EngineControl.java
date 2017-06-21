package cad.cep.engine;

import cad.cep.model.JSONMessage;
import caddb.CadWeatherSystemDatabaseAPI;

/**
 * The Class EngineControl.
 */
public class EngineControl {
	
	/** The instance. */
	private static EngineControl instance = new EngineControl();
	
	/** The service. */
	private EsperService service;
	
	/**
	 * Instantiates a new engine control.
	 */
	private EngineControl(){
	//not needed
	}
	
	public EngineControl init(CadWeatherSystemDatabaseAPI db){
		service = CEPFactory.createNewService(db);
		return instance;
	}
	
	/**
	 * Sets the service. This method is needed if you want to use a different esper engine
	 *
	 * @param serive the new service
	 */
	public void setService(EsperService serive){
		this.service = serive;
	}
	
	/**
	 * Gets the single instance of EngineControl.
	 *
	 * @return single instance of EngineControl
	 */
	public static EngineControl getInstance(){
		return instance;
	}
	
	/**
	 * Send event. The Event will be added to the EventStream of the EsperService of this class.
	 *
	 * @param event the event
	 */
	public void sendEvent(JSONMessage event){
		service.sendEvent(event);
	}
	
	/**
	 * Destroy enigne. Closes the connection to the engine.
	 */
	public void destroyEnigne(){
		this.service.destroyConnection();
	}

}
