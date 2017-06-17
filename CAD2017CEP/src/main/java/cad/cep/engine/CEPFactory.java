package cad.cep.engine;

import cad.cep.exceptions.MoMException;
import cad.cep.milf.MoMSender;
import cad.cep.model.Alert;
import cad.cep.model.JSONMessage;

/**
 * A factory for creating CEP Engines.
 */
public final class CEPFactory {

	/** The service. */
	private static EsperService service;
	
	/** The sender. */
	private static MoMSender sender;

	/**
	 * Creates a new CEP Engine or returns the existing one.
	 *
	 * @return the esper service which can be used to call methods for the esper engine
	 */
	protected static EsperService createNewService(){
		if(service != null){
			return service;
		}
		sender = new MoMSender();
		service = new EsperService();
		service.registerAdditionalEvent(JSONMessage.class);
		addUpdateEvent(service);
		addHearthRiskStatement(service);
		addWinterWarnings(service);
		addSummerWarnings(service);
		addSpecialWarning(service);
		System.err.println("Service started");
		return service;
	}

	/**
	 * Switch MoMSender if you need to send messages in another mom.
	 *
	 * @param newSender the new sender
	 */
	public static void switchSender(MoMSender newSender){
		sender = newSender;
	}
	
	/**
	 * Adds the special warning to the Engine. These Warnings are for unusual weather.
	 *
	 * @param service the service which needs the warning
	 */
	private static void addSpecialWarning(EsperService service) {
		String tropical = "select * from JSONMessage where humidity >= 90";
		service.createStatement(tropical, (newData, oldData)->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			sendWarning(message.getPlz(), "Tropical", "T1", "Humidity is over 90");
		});
	}
	
	/**
	 * Adds the update event. This event checks every new Message.
	 *
	 * @param service the service which needs the warning
	 */
	private static void addUpdateEvent(EsperService service) {
		String statementQuery ="select * from JSONMessage";
		service.createStatement(statementQuery, (newData, oldData) ->{
			JSONMessage underlying = (JSONMessage)newData[0].getUnderlying();
			System.out.println("Sending");
			System.out.println(underlying.toString());
			try {
				sender.send(underlying.getTopic()+"/CEP", underlying, true);
			} catch (MoMException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Adds the winter warnings. These warnings are needed to check for low temperatures, frost or snow.
	 *
	 * @param service the service which needs the warning
	 */
	public static void addWinterWarnings(EsperService service){
		String frostRisk = "select * from pattern[m1=JSONMessage ->m2=JSONMessage(m1.plz = m2.plz and m1.temperature < 0 and m2.temperature < 0) where timer:within(1 hour)]";
		service.createStatement(frostRisk, (newData, oldData)->{
			System.out.println("Its cold");
			JSONMessage message =(JSONMessage) newData[0].get("m2");
			sendWarning(message.getPlz(), "Frost", "W1", "There is a frost chance");
		});
		String extremeCold = "Select * from JSONMessage where temperature <=-9";
		service.createStatement(extremeCold, (newData, oldData)->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			sendWarning(message.getPlz(), "Cold", "W2", "The Temperature is " + message.getTemperature() + " wear warm clothing");
		});
		String heavySnow = "select * from JSONMessage where currentWeatherId = 622";
		service.createStatement(heavySnow, (newData, oldData) ->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			sendWarning(message.getPlz(), "Heavy Snowfall", "W3", "Heavy Snowfall, be carefull");
		});
	}
	
	/**
	 * Send warning. Sends the warning to the MoM. It uses the MoMSender of this class. 
	 *
	 * @param plz the plz of the city which the warning is for
	 * @param title the title of the warning
	 * @param code the code of the warning
	 * @param message the message for the client
	 */
	private static void sendWarning(String plz, String title, String code, String message){
		try{
			Alert alert = new Alert(title, code, message);
			sender.send(String.format("%s/%s", plz, "alert"), alert, false);
			System.out.println(alert);
		}catch(MoMException e){
			e.printStackTrace();
		}
	}

	/**
	 * Adds the summer warnings. These "warnings" are for good weather, heat and other summer releated things
	 *
	 * @param service the service which needs the warning
	 */
	private static void addSummerWarnings(EsperService service){
		String goSwimming = "Select * from JSONMessage where temperature >25 and currentWeatherId >= 800 and currentWeatherId <=804";
		service.createStatement(goSwimming, (newData, oldData)->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			sendWarning(message.getPlz(), "Nice Day", "S1", "The Weather looks nice, go swimming");
		});

	}

	/**
	 * Adds the hearth risk statement. 
	 *
	 * @param service the service which needs the warning
	 */
	private static void addHearthRiskStatement(EsperService service){
		String pressureSwitchRisk = "select * from pattern[p1=JSONMessage -> p2=JSONMessage(p1.plz = p2.plz and (p1.pressure - p2.pressure <-10 or p2.pressure - p1.pressure <-10 )) where timer:within(30 min)]"; 
		service.createStatement(pressureSwitchRisk, (newData, oldData)->{
			System.out.println("WARING pressure changes to much");
			JSONMessage message = (JSONMessage) newData[0].get("p2");
			sendWarning(message.getPlz(), "HearthRisk", "H1", "Pressure changes to rapitly, please take medication if needed");
			System.out.println("At " + message.getPlz());
		});
		String toHotRisk = "select * from JSONMessage where temperature >=25";
		service.createStatement(toHotRisk, (newData, oldData)->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			System.out.println("TO HOT");
			sendWarning(message.getPlz(), "HearthRisk", "H2", "Temperature over 25 degree, please take medication if needed");
		});
	}


}
