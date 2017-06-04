package cad.cep.engine;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.mockito.Mockito.*;

import cad.cep.model.JSONMessage;

public class EngineControlTest {

	@Test
	public void EngineTest() {
		
		//buildupp
		EngineControl control = EngineControl.getInstance();
		//check if method worked
		assertTrue(control != null);
		//create a mock for esper service
		EsperService service = mock(EsperService.class);
		control.setService(service);
		//create Mock
		JSONMessage message = mock(JSONMessage.class);
		//test event sending
		control.sendEvent(message);
		control.destroyEnigne();
	}

}
