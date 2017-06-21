package cad.cep.milf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import cad.cep.exceptions.MoMException;
import cad.cep.milf.util.PLZUtil;
import cad.cep.mom.MQTTMom;
import cad.cep.mom.MomFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class InformationLifeFlow.
 */
public class InformationLifeFlow {

	/**
	 * The main method. Reads the hardcoded PLZ and creates new Reader to read for messages.
	 *
	 * @param args the arguments
	 * @throws MoMException 
	 */
	public static void main(String[] args) throws MoMException {
		addnewReader("cep/ndamen");	
		MQTTMom tempMom = (MQTTMom) MomFactory.createOrLoadMom();
		
		List<String> plzs = PLZUtil.getAllKnownPLZ();
		for (String plz : plzs) {
			String weekly = plz+"/weekly";
			String plzTopic = plz+"/today";
			addnewReader(plzTopic);
			addnewReader(weekly);
			System.out.println(plz + " added");
		}
	}


	/**
	 * Add a new reader.
	 *
	 * @param topic the topic
	 * @return the MoM reader
	 */
	private static MoMReader addnewReader(String topic){
		try {
			MoMReader reader = new MoMReader(topic);
			reader.start();
			return reader;
		} catch (MoMException e) {
			//logging
			e.printStackTrace();
		}
		return null;
	}

}
