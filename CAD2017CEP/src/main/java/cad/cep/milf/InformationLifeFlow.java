package cad.cep.milf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cad.cep.exceptions.MoMException;

public class InformationLifeFlow {

	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			prop.load(InformationLifeFlow.class.getClassLoader().getResourceAsStream("config.properties"));
			String[] locations = prop.getProperty("locations").split(",");
			List<MoMReader> topicsReader = new ArrayList<>();
			for (int i = 0; i < locations.length; i++) {
//				plz + "/weekly"
				//today
				System.out.println(locations[i]);
				String topic = String.format("%s/%s", locations[i], "weekly");
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
