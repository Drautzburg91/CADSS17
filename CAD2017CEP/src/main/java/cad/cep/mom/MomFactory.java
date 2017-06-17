package cad.cep.mom;

import cad.cep.exceptions.MoMException;

/**
 * A factory for creating Mom objects.
 */
public final class MomFactory {

	/** The mqtt mom. */
	private static MQTTMom mqttMom;
	/**
	 * Instantiates a new mom factory.
	 */
	private MomFactory(){/* not needed */}
	/**
	 * Creates a new Mom object.
	 *
	 * @return the new MoM instance
	 * @throws MoMException the MoM exception
	 */
	public static IMoM createOrLoadMom() throws MoMException{
		try{
			if(mqttMom == null){
				String host = String.format("tcp://%s", System.getenv("MOM_HOST"));
				String user = System.getenv("MOM_USER");
				String pw = System.getenv("MOM_PW");
				mqttMom =	new MQTTMom(host, user, pw);
			}
			return mqttMom;
		} catch (Exception e) {
			throw new MoMException("MOM not found", e);
		}
	}
}
