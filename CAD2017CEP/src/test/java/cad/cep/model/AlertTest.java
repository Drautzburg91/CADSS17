package cad.cep.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AlertTest {

	@Test
	public void test() {
		Alert alert = new Alert("Test", "JB007", "I am a Test");
		assertEquals("Test", alert.getTitle());
		assertEquals("JB007", alert.getCode());
		assertEquals("I am a Test", alert.getWarning());
		Alert other = (Alert) alert.createMessage(alert.toString().getBytes(), "test");
		assertEquals(alert.getTitle(), other.getTitle());
	}

}
