package cad.cep.engine;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import cad.cep.db.WeatherRepository;
import cad.cep.db.WeatherRepositoryImpl;
import cad.cep.milf.MoMSender;
import cad.cep.model.JSONMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class CEPFactoryTest.
 */
public class CEPFactoryTest {

	/** The service. */
	private EsperService service;
	
	/** The mom. */
	private MoMSender mom;
	
	/**
	 * Prepeare test.
	 * @throws SQLException 
	 */
	@Before
	public void prepeareTest() throws SQLException{
		WeatherRepository dbMock = mock(WeatherRepositoryImpl.class);
		ResultSet set = mock(ResultSet.class);
		when(set.next()).thenReturn(true).thenReturn(false);
		when(set.getDouble(any(String.class))).thenReturn(10.0);
		when(set.getInt(any(String.class))).thenReturn(1000);
		when(dbMock.selectWeatherByCityAndTimePeriod(anyInt(), any(Timestamp.class), any(Timestamp.class))).thenReturn(set);
		service = CEPFactory.createNewService(dbMock);
		mom = mock(MoMSender.class);
		List<MoMSender> sender = new ArrayList();
		sender.add(mom);
		CEPFactory.switchSender(sender);
	}
	
	/**
	 * Test event sending.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testEventSending() {
		//Create JSON Message to send
		JSONMessage messageEvent = new JSONMessage();
		//send it
		service.sendEvent(messageEvent);
		//if no exception Test is successfull
		service.sendEvent(null);
	}
	
	/**
	 * Test W inter warning.
	 */
	@Test
	public void testWInterWarning(){
		JSONMessage message1 = new JSONMessage();
		message1.setPlz("111111");
		message1.setTemperature(-100.0);
		service.sendEvent(message1);
		service.sendEvent(message1);
		message1.setCurrentWeatherId(622);
		service.sendEvent(message1);
	}

	/**
	 * Test special warning.
	 */
	@Test
	public void testSpecialWarning(){
		//needs a JSON event
		JSONMessage message = new JSONMessage();
		//When Humitidy is > 90 a listener gets a call
		message.setHumidity(100);
		message.setCityName("TestCity");
		//send it
		service.sendEvent(message);
	}

	/**
	 * Test summer warning.
	 */
	@Test
	public void testSummerWarning(){
		//need JSON Object
		JSONMessage message = new JSONMessage();
		message.setPlz("111111");
		message.setCurrentWeatherId(801);
		message.setTemperature(26.0);
		service.sendEvent(message);
	}
	
	/**
	 * Test hearth risk warning.
	 */
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
		warning.setTemperature(30.0);
		warning.setPressure(30);
		service.sendEvent(message);
		service.sendEvent(warning);
	}

	/**
	 * Destroy.
	 */
	@AfterClass
	public static void destroy(){
		WeatherRepository dbMock = mock(WeatherRepositoryImpl.class);
		CEPFactory.createNewService(dbMock).destroyConnection();
	}

}
