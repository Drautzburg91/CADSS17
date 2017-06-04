package cad.cep.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import cad.cep.db.CAD_WEATHER_SYSTEM_DATABASE_API;
import cad.cep.model.IMessage;

public class Database implements IDatabase{
	
	private EmbeddedDatabase database;
	private CAD_WEATHER_SYSTEM_DATABASE_API api;
	
	public Database(CAD_WEATHER_SYSTEM_DATABASE_API api) throws FileNotFoundException, IOException{
		this.api = api;
		Properties config = new Properties();
		config.load(new FileInputStream("/resources/db.properties"));
	}
	
	public boolean isConnected() {
		return database != null;
	}

	@Override
	public IDatabase connectToDataBase(String path) {
		database = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
		return this;
	}

	@Override
	public IDatabase saveMessage(IMessage message) {
//		api.insert_Weather(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17)
		return null;
	}

	@Override
	public IMessage readMessage(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
