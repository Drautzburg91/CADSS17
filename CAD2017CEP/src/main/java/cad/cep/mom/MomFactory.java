package cad.cep.mom;

import cad.cep.exceptions.MoMException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Mom objects.
 */
public final class MomFactory {

	private static MQTTMom mqttMom;
	/**
	 * Instantiates a new mom factory.
	 */
	private MomFactory(){/* not needed */}

	/**
	 * Creates a new Mom object.
	 *
	 * @return the i mo M
	 * @throws MoMException the mo M exception
	 */
	public static IMoM createOrLoadMom() throws MoMException{
		try{
			if(mqttMom == null){
				mqttMom =	new MQTTMom(System.getenv("MOM_HOST"), System.getenv("MOM_USER"), System.getenv("MOM_PW"));
			}
			return mqttMom;
		} catch (Exception e) {
			throw new MoMException("MOM not found", e);
		}
	}
}
