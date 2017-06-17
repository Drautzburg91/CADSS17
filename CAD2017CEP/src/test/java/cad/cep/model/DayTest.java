package cad.cep.model;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class DayTest {

	@Test
	public void test() throws ParseException {
		Day day = new Day();
		setTestValues(day);
		testTestValues(day);
		assertFalse(day.equals(new JSONMessage()));
		assertTrue(day.equals(day));
		assertTrue(day.compareTo(day) == 0);
		int code =  day.hashCode();
		day.setPlz(null);
		assertTrue(code != day.hashCode());
	}

	private void testTestValues(Day day) {
		assertEquals("TestName", day.getCityName());
		assertEquals("Fire rains from the sky", day.getCurrentWeather());
		assertEquals(666, day.getCurrentWeatherId());
		assertEquals("2017-06-17", day.getDate());
		assertEquals("000000", day.getPlz());
		assertEquals("no icon found", day.getWeatherIcon());
		assertTrue(day.getMinTemperature() >=666.00);
		assertTrue(day.getMaxTemperature() >= 10000.56);
		assertTrue(day.getTemperature() >= 666);
	}

	private void setTestValues(Day day) throws ParseException {
		day.setCityName("TestName");
		day.setCurrentWeather("Fire rains from the sky");
		day.setCurrentWeatherId(666);
		try {
			day.setDate("error");
		} catch (Exception e) {
			e.printStackTrace();
		}
		day.setDate("2017-06-17");
		day.setMaxTemperature(10000.56);
		day.setMinTemperature(666.00);
		day.setTemperature(666);
		day.setPlz("000000");
		day.setWeatherIcon("no icon found");
	}

}
