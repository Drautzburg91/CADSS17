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
			byte[] payload = message.getPayload();
			String string = new String(payload);
			if("hallo".equals(string)){
				System.out.println("MoMbased Information Life Flow started working");
				return;
			}
			try {
				System.out.println("got");
				System.err.println(new String(payload));
				json.createMessage(payload, topic);
//				json.setTopic(topic);
				EngineControl.getInstance().sendEvent(json);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println(token.getMessageId());
		
	}

}
