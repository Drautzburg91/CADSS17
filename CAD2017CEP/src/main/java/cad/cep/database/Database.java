package cad.cep.database;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import cad.cep.model.IMessage;

public class Database implements IDatabase{
	
	private EmbeddedDatabase database;
	@Override
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
