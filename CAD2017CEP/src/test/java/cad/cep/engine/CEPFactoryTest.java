package cad.cep.engine;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import cad.cep.exceptions.MoMException;
import cad.cep.milf.MoMSender;
import cad.cep.model.IMessage;
import cad.cep.model.JSONMessage;

public class CEPFactoryTest {

	private EsperService service;
	private MoMSender mom;
	@Before
	public void prepeareTest(){
		service = CEPFactory.createNewService();
		mom = mock(MoMSender.class);
		CEPFactory.switchSender(mom);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testEventSending() {
		//Create JSON Message to send
		JSONMessage messageEvent = new JSONMessage();
		//send it
		service.sendEvent(messageEvent);
		//if no exception Test is successfull
		service.sendEvent(null);
	}
	@Test
	public void testWInterWarning(){
		JSONMessage message1 = new JSONMessage();
		message1.setPlz("111111");
		message1.setTemperature(-100);
		service.sendEvent(message1);
		service.sendEvent(message1);
		message1.setCurrentWeatherId(622);
		service.sendEvent(message1);
	}
	
	@Test
	public void testSpecialWarning(){
		//needs a JSON event
		JSONMessage message = new JSONMessage();
		//When Humitidy is > 90 a listener gets a call
		message.setHumitidy(100);
		message.setCityName("TestCity");
		//send it
		service.sendEvent(message);
		try {
			doThrow(new MoMException("Test", new IOException())).when(mom).send(anyString(), any(IMessage.class), true);
			service.sendEvent(message);
		} catch (MoMException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSummerWarning(){
		//need JSON Object
		JSONMessage message = new JSONMessage();
		message.setPlz("111111");
		message.setCurrentWeatherId(801);
		message.setTemperature(26);
		service.sendEvent(message);
	}
	@Test
	public void testHearthRiskWarning(){
		JSONMessage message = new JSONMessage();
		message.setCityName("TestCity");
		message.setPlz("1111111");
		message.setTemperature(20);
		message.setPressure(20);
		JSONMessage warning = new JSONMessage();
		warning.setCityName("TestCity");
		warning.setPlz("1111111");
		warning.setTemperature(30);
		warning.setPressure(30);
		service.sendEvent(message);
		service.sendEvent(warning);
	}
	
	@AfterClass
	public static void destroy(){
		CEPFactory.createNewService().destroyConnection();
	}

}
