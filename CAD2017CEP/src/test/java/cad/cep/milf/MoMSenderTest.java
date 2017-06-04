package cad.cep.milf;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import cad.cep.exceptions.MoMException;
import cad.cep.mom.IMoM;

public class MoMSenderTest {

	@Test
	public void test() throws MoMException {
		MoMSender sender = new MoMSender();
		IMoM mockMom = mock(IMoM.class);
		sender.setMom(mockMom);
		sender.send("Test", null);
	}

}
