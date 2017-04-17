package cad.cep.mom;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
public class RabbitMom implements IMoM{
	
	/** The channel. */
	private Channel channel;
	
	/** The connection. */
	private Connection connection;

	/**
	 * Instantiates a new rabbit mom.
	 *
	 * @param host the host
	 * @throws MoMException the mo M exception
	 */
	protected RabbitMom(String host) throws MoMException {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			connection = factory.newConnection();
			channel = connection.createChannel();
		} catch (IOException | TimeoutException e) {
			//TODO Logging should be here
			throw new MoMException("Could not connect to MoM", e);
		}
	}

	/* (non-Javadoc)
	 * @see cad.cep.mom.IMoM#readMessageFromTopic(java.lang.String)
	 */
	@Override
	public final IMessage readMessageFromTopic(String topic) throws MoMException {
		try {
			//one message at the time
			channel.basicQos(1);
			channel.exchangeDeclare("MILF", "topic");
			String queue = channel.queueDeclare().getQueue();
			channel.queueBind(queue, "MILF", topic);
			Consumer consumer = new DefaultConsumer(channel){
				@Override
				  public void handleDelivery(String consumerTag, Envelope envelope,
				                             AMQP.BasicProperties properties, byte[] body)
				      throws IOException {
					 JSONMessage json = (JSONMessage) new JSONMessage().createMessage(body);
					 //Message can be deleted
					 channel.basicAck(envelope.getDeliveryTag(), false);
					 System.out.println(json.toString());
				  }
			};
			channel.basicConsume(queue, true, consumer);
		} catch (IOException e) {
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
			channel.exchangeDeclare("MILF", "topics");
			String queueName = channel.queueDeclare().getQueue();
			String routingKey = "city.location.stuff";
			channel.queueBind(queueName, "MILF", routingKey);
			channel.basicPublish("MILF", routingKey, null, message.toString().getBytes());
		} catch (IOException e) {
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
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			// TODO Logging
			throw new MoMException("Cannot close connection", e);
		}
	}

}
