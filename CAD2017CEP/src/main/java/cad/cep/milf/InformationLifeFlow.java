package cad.cep.milf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cad.cep.exceptions.MoMException;

public class InformationLifeFlow {

	public static void main(String[] args) {
		List<String> plzs = new ArrayList<>();
		plzs.add("24103");
		plzs.add("19055");
		plzs.add("20095");
		plzs.add("28215");
		plzs.add("30159");
		plzs.add("14467");
		plzs.add("10785");
		plzs.add("39104");
		plzs.add("40213");
		plzs.add("65183");
		plzs.add("99084");
		plzs.add("01069");
		plzs.add("55116");
		plzs.add("66111");
		plzs.add("70173");
		plzs.add("78467");
		plzs.add("80331");
		
		for (String plz : plzs) {
			String weekly = plz+"/weekly";
			String plzTopic = plz+"/today";
			addnewReader(plzTopic);
			addnewReader(weekly);
			System.out.println(plz + " added");
		}
		
	}

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
