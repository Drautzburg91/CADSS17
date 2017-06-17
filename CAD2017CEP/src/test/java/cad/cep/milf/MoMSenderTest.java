package cad.cep.milf;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import cad.cep.exceptions.MoMException;
import cad.cep.mom.IMoM;

// TODO: Auto-generated Javadoc
/**
 * The Class MoMSenderTest.
 */
public class MoMSenderTest {

	/**
	 * Test.
	 *
	 * @throws MoMException the mo M exception
	 */
	@Test
	public void test() throws MoMException {
		MoMSender sender = new MoMSender();
		IMoM mockMom = mock(IMoM.class);
		sender.setMom(mockMom);
		sender.send("Test", null, true);
	}

}
