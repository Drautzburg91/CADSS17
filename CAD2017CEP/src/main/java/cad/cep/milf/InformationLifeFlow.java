package cad.cep.milf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cad.cep.engine.CEPFactory;
import cad.cep.engine.EngineControl;
import cad.cep.engine.EsperService;
import cad.cep.exceptions.MoMException;
import cad.cep.model.JSONMessage;

public class InformationLifeFlow {

	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			prop.load(InformationLifeFlow.class.getClassLoader().getResourceAsStream("config.properties"));
			String[] locations = prop.getProperty("locations").split(",");
			List<MoMReader> topicsReader = new ArrayList<>();
			for (int i = 0; i < locations.length; i++) {
				String topic = String.format("%s/%s", locations[i], "today");
				topicsReader.add(addnewReader(topic));
			}
		} catch (IOException e) {
			e.printStackTrace();
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
