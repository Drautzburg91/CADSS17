package cad.cep.milf;

import cad.cep.exceptions.MoMException;
import cad.cep.mom.IMoM;
import cad.cep.mom.MomFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class MoMReader.
 */
public class MoMReader extends Thread{
	
	public String topic;
	private IMoM mom;
	
	public MoMReader(String topic) throws MoMException{
		this.topic = topic;
		mom = MomFactory.createOrLoadMom();
	}

	/**
	 * Read.
	 *
	 * @throws MoMException the mo M exception
	 */
	public void read() throws MoMException{
		mom.readMessageFromTopic(topic);
	}

	@Override
	public void run() {
		try {
				this.read();
		} catch (MoMException e) {
			//loging here
			e.printStackTrace();
		}
		
	}
	
	
}
