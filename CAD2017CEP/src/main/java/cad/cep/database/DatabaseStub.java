package cad.cep.database;

import cad.cep.model.IMessage;

public class DatabaseStub implements IDatabase{

	@Override
	public final boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final IDatabase connectToDataBase(String path) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public final IDatabase saveMessage(IMessage message) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public final IMessage readMessage(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
