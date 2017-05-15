package cad.cep.mom;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import cad.cep.exceptions.MoMException;
import cad.cep.model.IMessage;
import cad.cep.model.JSONMessage;

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
	protected MQTTMom(String host, String id) throws MoMException {
			try {
				client = new MqttClient(host, MqttClient.generateClientId());
				client.connect();
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
	//TODO Rewritto so that supports topic mechanic
	public final void sendMessageToopic(String queue, IMessage message) throws MoMException {
		try {
			MqttMessage payload = new MqttMessage(message.toString().getBytes());
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
			client.disconnect();
		} catch (Exception e) {
			// TODO Logging
			throw new MoMException("Cannot close connection", e);
		}
	}

}
