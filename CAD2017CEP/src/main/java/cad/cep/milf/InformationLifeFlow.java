package cad.cep.milf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cad.cep.engine.CEPFactory;
import cad.cep.engine.EngineControl;
import cad.cep.engine.EsperService;
import cad.cep.exceptions.MoMException;
import cad.cep.model.JSONMessage;

public class InformationLifeFlow {

	public static void main(String[] args) {
		//need method to read topiclist somewhere
		List<String> topics = new ArrayList<>();
		List<MoMReader> topicReader = new ArrayList<>();
		topics.add("today");
		for (String topic : topics) {
			topicReader.add(addnewReader(topic));
		}
	}

	private static MoMReader addnewReader(String topic){
		try {
			MoMReader reader = new MoMReader(topic, topic+"reader");
			reader.start();
			return reader;
		} catch (MoMException e) {
			//logging
			e.printStackTrace();
		}
		return null;
	}

}
