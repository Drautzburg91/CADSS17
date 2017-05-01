package cad.cep.mom;

import cad.cep.exceptions.MoMException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Mom objects.
 */
public final class MomFactory {

	/** The Constant HOST. */
	//TODO use real host
	private static final String HOST = "ec2-52-24-244-51.us-west-2.cpmpute.amazonaws.com";

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
	public static IMoM createMom(String id) throws MoMException{
		return new MQTTMom(HOST, id);
	}
}
