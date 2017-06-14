package cad.cep.mom;

import java.util.Properties;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import cad.cep.exceptions.MoMException;
import cad.cep.model.IMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class RabbitMom.
 */
public class MQTTMom implements IMoM{

	private MqttClient client;

	/**
	 * Instantiates a new rabbit mom.
	 *
	 * @param host the host
	 * @throws MoMException the mo M exception
	 */
	protected MQTTMom(String host, String user, String pw) throws MoMException {
			try {
				client = new MqttClient(host, MqttClient.generateClientId());
				MqttConnectOptions options = new MqttConnectOptions();
				options.setPassword(pw.toCharArray());
				options.setUserName(user);
				client.connect(options);
			} catch (MqttException e) {
				throw new MoMException("Cannot connect to MoM", e);
			}
	}

	/* (non-Javadoc)
	 * @see cad.cep.mom.IMoM#readMessageFromTopic(java.lang.String)
	 */
	@Override
	public final synchronized IMessage readMessageFromTopic(String topic) throws MoMException {
		try {
			//one message at the time
			client.setCallback(new CallBack());
			client.subscribe(topic);
		} catch (Exception e) {
			// TODO Logging
			throw new MoMException("Cannot read topic: " +topic, e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cad.cep.mom.IMoM#sendMessageToopic(java.lang.String, cad.cep.model.IMessage)
	 */
	@Override
	public final void sendMessageTopic(String queue, IMessage message, boolean retained) throws MoMException {
		try {
			MqttMessage payload = new MqttMessage(message.toString().getBytes());
			payload.setRetained(retained);
			client.publish(queue, payload);
		} catch (Exception e) {
			//TODO LOGING
			throw new MoMException("Can not send message", e);
		}
	}

	/* (non-Javadoc)
	 * @see cad.cep.mom.IMoM#closeConnection()
	 */
	@Override
	public void closeConnection() throws MoMException {
		try {
			System.out.println("Closing connection");
			client.disconnect();
		} catch (Exception e) {
			throw new MoMException("Cannot close connection", e);
		}
	}
	public void reConnect() throws MoMException{
		try {
			client.reconnect();
		} catch (MqttException e) {
			throw new MoMException("reconnect failed", e);
		}
	}

}
