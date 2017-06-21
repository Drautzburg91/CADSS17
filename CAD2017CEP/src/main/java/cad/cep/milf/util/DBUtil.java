package cad.cep.milf.util;

import cad.cep.db.WeatherRepository;
import cad.cep.db.WeatherRepositoryImpl;

public final class DBUtil {

	private static WeatherRepository db;

	private DBUtil(){
		//not needed
	}
	
	public static WeatherRepository getDB(){
		if(db == null){
			db = new WeatherRepositoryImpl();
		}
		return db;
	}
}
