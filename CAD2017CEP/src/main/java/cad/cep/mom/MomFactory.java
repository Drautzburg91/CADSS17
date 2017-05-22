package cad.cep.mom;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import cad.cep.exceptions.MoMException;
import cad.cep.milf.util.PropertiesUtil;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Mom objects.
 */
public final class MomFactory {

	private static final String CONFIG_PROPERTIES = "config.properties";
	/** The Constant HOST. */
	//TODO use real host
	private static final String HOST = "tcp://ec2-35-163-22-190.us-west-2.compute.amazonaws.com:1883";

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
		try{
			Properties config = PropertiesUtil.loadProperty(CONFIG_PROPERTIES);
			return new MQTTMom(HOST, id, config);
		} catch (IOException e) {
			throw new MoMException("Property not found", e);
		}
	}
}
