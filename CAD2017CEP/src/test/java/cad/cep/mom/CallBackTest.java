package cad.cep.mom;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;

import cad.cep.engine.EngineControl;
import cad.cep.model.JSONMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class CallBackTest.
 */
public class CallBackTest {

	/**
	 * Test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void test() throws Exception {
		//Buildup create the mock needed
		EngineControl mockControl = mock(EngineControl.class);
		//create the callBack with the mock
		CallBack callBack = new CallBack(mockControl);
		callBack.connectionLost(new IOException("TEST EXCEPTION"));
		IMqttDeliveryToken token = mock(MqttDeliveryToken.class);
		callBack.deliveryComplete(token);
		//real test
		testCallBackMethod(callBack);
		//destroy
		mockControl.destroyEnigne();
	}

	/**
	 * Test call back method.
	 *
	 * @param callBack the call back
	 * @throws Exception the exception
	 */
	private void testCallBackMethod(CallBack callBack) throws Exception {
		
		MqttMessage message = mock(MqttMessage.class);
		try {
			//There needs to be an exception
			callBack.messageArrived("TEST", message);
		} catch (Exception e) {
			System.err.println("THAT SHOUL HAPPEN");
		}
		
		try {
			//"TEST" should cause an exception
			when(message.getPayload()).thenReturn("EXCEPTION".getBytes());
			callBack.messageArrived("TEST", message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//"hello" returns nothing
		when(message.getPayload()).thenReturn("hallo".getBytes());
		callBack.messageArrived("TEST", message);
		//need a json message to cast
		JSONMessage json = new JSONMessage();
		json.setCityName("test");
		json.setCurrentWeather("test");
		//can´t verify if sended, but if there is no exception it should have worked
		when(message.getPayload()).thenReturn(json.toString().getBytes());
		callBack.messageArrived("TEST", message);
	}

}
