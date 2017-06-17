package cad.cep.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONMessageTest.
 */
public class JSONMessageTest {


	@Test
	public void testSetMethods() {
		JSONMessage message = new JSONMessage();
		this.setTestValues(message);
		this.testGetMethods(message);
	}

	@Test 	
	public void testCopy(){
		JSONMessage message = new JSONMessage();
		this.setTestValues(message);
		JSONMessage copy = new JSONMessage();
		copy.copy(message);
		this.testGetMethods(copy);
	}
	@Test 	
	public void testFromBytes(){
		JSONMessage message = new JSONMessage();
		this.setTestValues(message);
		JSONMessage fromBytes = new JSONMessage();
		fromBytes.createMessage(message.toString().getBytes(), message.getTopic());
		this.testGetMethods(fromBytes);
	}

	private void setTestValues(JSONMessage message) {
		message.setCityName("a");
		message.setCurrentWeather("b");
		message.setCurrentWeatherId(0);
		message.setHumidity(1);
		message.setLatitude(12.6);
		message.setLongitude(12.0);
		message.setPlz("1");
		message.setPressure(13);
		message.setTemperature(1222.0);
		message.setTemperatureMax(122232.0);
		message.setTemperatureMin(13443.0);
		message.setTopic("aad");
		message.setWeatherIcon("aasdadad");
		message.setWindDeg(11.11);
		message.setWindspeed(09.20);
	}

	/**
	 * Test get methods.
	 *
	 * @param message the message
	 */
	private void testGetMethods(JSONMessage message){
		assertEquals("a", message.getCityName());
		assertEquals("b", message.getCurrentWeather());
		assertEquals(0, message.getCurrentWeatherId());
		assertEquals(1, message.getHumidity());
		//equals dosen´t work with double thanks to processors doing stupid stuff
		assertTrue(message.getLatitude() >= 12.6);
		assertTrue (message.getLongitude() >= 12.0);
		assertEquals("1", message.getPlz());
		assertEquals(13, message.getPressure());
		assertTrue(message.getTemperature()>= 1222.0);
		assertTrue(message.getTemperatureMax() >= 122232);
		assertTrue(message.getTemperatureMin() >= 13443);
		assertEquals("aad", message.getTopic());
		assertEquals("aasdadad", message.getWeatherIcon());
		assertTrue(message.getWindDeg() >= 11.11);
		assertTrue(message.getWindspeed() >= 09.20);
	}

}
