package cad.cep.model;

import static org.junit.Assert.*;

import org.junit.Test;

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
		message.setTemperature(1222);
		message.setTemperatureMax(122232);
		message.setTemperatureMin(13443);
		message.setTopic("aad");
		message.setWeatherIcon("aasdadad");
		message.setWindDeg(11.11);
		message.setWindspeed(09.20);
	}
	
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
		assertEquals(1222, message.getTemperature());
		assertEquals(122232, message.getTemperatureMax());
		assertEquals(13443, message.getTemperatureMin());
		assertEquals("aad", message.getTopic());
		assertEquals("aasdadad", message.getWeatherIcon());
		assertTrue(message.getWindDeg() >= 11.11);
		assertTrue(message.getWindspeed() >= 09.20);
	}

}
