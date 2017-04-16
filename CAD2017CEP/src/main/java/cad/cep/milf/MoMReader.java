package cad.cep.milf;

import cad.cep.exceptions.MoMException;
import cad.cep.mom.IMoM;
import cad.cep.mom.MomFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class MoMReader.
 */
public class MoMReader {

	/**
	 * Read.
	 *
	 * @throws MoMException the mo M exception
	 */
	public void read() throws MoMException{
		IMoM mom =  MomFactory.createMom();
		mom.readMessageFromTopic("TPOC");
		mom.sendMessageToopic("aa", null);
	}
}
