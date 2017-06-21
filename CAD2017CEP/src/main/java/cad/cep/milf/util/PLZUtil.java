package cad.cep.milf.util;

import java.util.ArrayList;
import java.util.List;

public final class PLZUtil {

	private PLZUtil(){
		//not needed but sonar wants it
	}

	public static List<String> getAllKnownPLZ(){
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
		return plzs;
	}
}
