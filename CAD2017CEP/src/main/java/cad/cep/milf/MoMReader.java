package cad.cep.milf;

import cad.cep.exceptions.MoMException;
import cad.cep.mom.IMoM;
import cad.cep.mom.MomFactory;

/**
 * The Class MoMReader.
 */
public class MoMReader extends Thread{
	
	/** The topic. */
	public String topic;
	
	/** The mom. */
	private IMoM mom;
	
	/**
	 * Instantiates a new MoM reader. The Reader looks for any message in this topic
	 *
	 * @param topic the topic
	 * @throws MoMException the MoM exception
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
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
