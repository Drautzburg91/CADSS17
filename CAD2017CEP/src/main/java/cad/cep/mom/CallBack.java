package cad.cep.mom;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import cad.cep.engine.EngineControl;
import cad.cep.model.JSONMessage;

public class CallBack implements MqttCallback{


	@Override
	public void connectionLost(Throwable cause) {
			System.err.println(cause.getMessage());
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
			JSONMessage json = new JSONMessage();
//			json.createMessage(message.getPayload());
//			EngineControl.getInstance().sendEvent(json);
			System.out.println(new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println(token.getMessageId());
		
	}

}
