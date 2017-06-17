package cad.cep.mom;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import cad.cep.engine.EngineControl;
import cad.cep.exceptions.MoMException;
import cad.cep.milf.MoMSender;
import cad.cep.model.JSONMessage;
import cad.cep.model.WeeklyForcast;

/**
 * The Class CallBack.
 */
public class CallBack implements MqttCallback{
	
	 /** The control. */
 	private EngineControl control;

	/**
	 * Instantiates a new call back.
	 */
	public CallBack() {
		control = EngineControl.getInstance();
	}
	 
 	/**
 	 * Instantiates a new call back.
 	 *
 	 * @param control the control
 	 */
 	public CallBack(EngineControl control){
		this.control = control;
		 
	 }

	/* (non-Javadoc)
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
	 */
	@Override
	public void connectionLost(Throwable cause) {
			try {
				MomFactory.createOrLoadMom().reConnect();
			} catch (MoMException e) {
				e.printStackTrace();
			}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
			byte[] payload = message.getPayload();
			String string = new String(payload);
			//MoM greets us with hello if startup is successful 
			if("hallo".equals(string)){
				System.out.println("MoMbased Information Life Flow started working");
				return;
			}
			
				System.out.println("got");
				System.err.println(new String(payload));
				try {
				if(topic.contains("today")){
				JSONMessage json = new JSONMessage();
				json.createMessage(payload, topic);
//				json.setTopic(topic);
				control.sendEvent(json);
				}else if(topic.contains("weekly")){
				WeeklyForcast forcast = new WeeklyForcast();
				forcast.createMessage(payload, topic);
				MoMSender sender = new MoMSender();
				sender.send(forcast.getPlz()+"/weekly/CEP", forcast, true);
				}
			} catch (Exception e) {
			e.printStackTrace();
				
			}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println(token.getMessageId());
		
	}

}
