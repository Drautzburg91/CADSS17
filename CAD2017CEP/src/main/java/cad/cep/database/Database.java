package cad.cep.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import cad.cep.model.IMessage;

public class Database implements IDatabase{
	
	private EmbeddedDatabase database;
	
	public Database() throws FileNotFoundException, IOException{
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMessage readMessage(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
