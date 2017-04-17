package cad.cep.database;

import cad.cep.model.IMessage;

// TODO: Auto-generated Javadoc
/**
 * The Interface IDatabase.
 */
public interface IDatabase {

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	boolean isConnected();
	
	/**
	 * Connect to data base.
	 *
	 * @param path the path
	 * @return the i database
	 */
	IDatabase connectToDataBase(String path);
	
	/**
	 * Save Message.
	 *
	 * @param message the message
	 * @return the i database
	 */
	IDatabase saveMessage(IMessage message);
	
	IMessage readMessage(String query);
}
