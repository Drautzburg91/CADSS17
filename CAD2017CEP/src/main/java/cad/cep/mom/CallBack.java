package cad.cep.mom;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import cad.cep.engine.EngineControl;
import cad.cep.model.JSONMessage;
import cad.cep.model.WeeklyForcast;

public class CallBack implements MqttCallback{
	
	 private EngineControl control;

	public CallBack() {
		control = EngineControl.getInstance();
	}
	 public CallBack(EngineControl control){
		this.control = control;
		 
	 }

	@Override
	public void connectionLost(Throwable cause) {
			System.err.println(cause.getMessage());
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
			byte[] payload = message.getPayload();
			String string = new String(payload);
			if("hallo".equals(string)){
				System.out.println("MoMbased Information Life Flow started working");
				return;
			}
			
				System.out.println("got");
				System.err.println(new String(payload));
				try {
				JSONMessage json = new JSONMessage();
				json.createMessage(payload, topic);
//				json.setTopic(topic);
				control.sendEvent(json);
			} catch (Exception e) {
				WeeklyForcast forcast = new WeeklyForcast();
				forcast.createMessage(payload, topic);
				System.out.println(forcast);
			}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println(token.getMessageId());
		
	}

}
