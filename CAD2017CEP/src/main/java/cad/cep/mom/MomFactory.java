package cad.cep.mom;

import cad.cep.exceptions.MoMException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Mom objects.
 */
public final class MomFactory {

	/** The Constant HOST. */
	//TODO use real host
	private static final String HOST = "host";

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
	public static IMoM createMom() throws MoMException{
		return new RabbitMom(HOST);
	}
}
