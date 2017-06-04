package cad.cep.engine;

import cad.cep.exceptions.MoMException;
import cad.cep.milf.MoMSender;
import cad.cep.model.JSONMessage;

public final class CEPFactory {

	private static EsperService service;
	private static MoMSender sender;

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

	public static void switchSender(MoMSender newSender){
		sender = newSender;
	}
	private static void addSpecialWarning(EsperService service) {
		String tropical = "select * from JSONMessage where humitidy >= 90";
		service.createStatement(tropical, (newData, oldData)->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			message.addWarning("WARNING Tropical pack a second shirt.");
			sendWarning(message, sender);
		});
	}
	private static void addUpdateEvent(EsperService service) {
		String statementQuery ="select * from JSONMessage";
		service.createStatement(statementQuery, (newData, oldData) ->{
			JSONMessage underlying = (JSONMessage)newData[0].getUnderlying();
			System.out.println("Sending");
			System.out.println(underlying.toString());
			try {
				sender.send(underlying.getTopic()+"-mobile", underlying);
			} catch (MoMException e) {
				e.printStackTrace();
			}
		});
	}
	public static void addWinterWarnings(EsperService service){
		String frostRisk = "select * from pattern[m1=JSONMessage ->m2=JSONMessage(m1.plz = m2.plz and m1.temperature < 0 and m2.temperature < 0) where timer:within(1 hour)]";
		service.createStatement(frostRisk, (newData, oldData)->{
			System.out.println("Its cold");
			JSONMessage message =(JSONMessage) newData[0].get("m2");
			message.addWarning("WARNING there is a Frost chance");
			sendWarning(message, sender);
		});
		String extremeCold = "Select * from JSONMessage where temperature <=-9";
		service.createStatement(extremeCold, (newData, oldData)->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			message.addWarning("WARNING Extreme Cold");
			sendWarning(message, sender);
		});
		String heavySnow = "select * from JSONMessage where currentWeatherId = 622";
		service.createStatement(heavySnow, (newData, oldData) ->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			message.addWarning("WARNING Extrem snowfall");
			sendWarning(message, sender);
		});
	}
	private static void sendWarning(JSONMessage message, MoMSender sender){
		try {
			sender.send(message.getTopic()+"/alert", message);
		} catch (MoMException e) {
			e.printStackTrace();
		}
	}

	private static void addSummerWarnings(EsperService service){
		//TODO define good swimming weather
		String goSwimming = "Select * from JSONMessage where temperature >25 and currentWeatherId >= 800 and currentWeatherId <=804";
		service.createStatement(goSwimming, (newData, oldData)->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			message.addWarning("INFO You should go swimming today");
			sendWarning(message, sender);
		});

	}

	private static void addHearthRiskStatement(EsperService service){
		//if the temperature rises from one day to another more than 5dec.
		String temperatueDiff = "select * from pattern[d1=JSONMessage ->d2=JSONMessage(d1.plz = d2.plz and d2.temperature - d1.temperature >=5) where timer:within(24 hours)]";
		service.createStatement(temperatueDiff, (newData, oldData)->{
			System.out.println("WARING Temperature Rises to much");
			//gets the message for today
			JSONMessage message = (JSONMessage) newData[0].get("d2");
			message.addWarning("Heart Risk temperature rises to fast");
			sendWarning(message, sender);
			System.out.println("At " + message.getPlz());
		});
		String pressureSwitchRisk = "select * from pattern[p1=JSONMessage -> p2=JSONMessage(p1.plz = p2.plz and (p1.pressure - p2.pressure <-10 or p2.pressure - p1.pressure <-10 )) where timer:within(30 min)]"; 
		service.createStatement(pressureSwitchRisk, (newData, oldData)->{
			System.out.println("WARING pressure changes to much");
			//gets the message for today
			JSONMessage message = (JSONMessage) newData[0].get("p2");
			message.addWarning("Heart Risk p");
			sendWarning(message, sender);
			System.out.println("At " + message.getPlz());
		});
		String toHotRisk = "select * from JSONMessage where temperature >=25";
		service.createStatement(toHotRisk, (newData, oldData)->{
			JSONMessage message = (JSONMessage) newData[0].getUnderlying();
			System.out.println("TO HOT");
			message.addWarning("WARNING To Hot, if needed take medication for blood pressure");
			sendWarning(message, sender);
		});
	}


}
