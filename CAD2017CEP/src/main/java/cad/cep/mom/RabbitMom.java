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
	public final IMessage readMessageFromTopic(String queue) throws MoMException {
		try {
			channel.queueDeclare(queue, false, false, false, null);
			Consumer consumer = new DefaultConsumer(channel){
				//TODO Get it to work.
				 private JSONMessage json;

				 public JSONMessage getJson(){
					 return json;
				 }
				@Override
				  public void handleDelivery(String consumerTag, Envelope envelope,
				                             AMQP.BasicProperties properties, byte[] body)
				      throws IOException {
					 json = (JSONMessage) new JSONMessage().createMessage(body);
				  }
			};
			
		} catch (IOException e) {
			// TODO Logging
			throw new MoMException("Cannot read topic: " +queue, e);
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
			//TODO figure out what the hell the arguments do
			channel.queueDeclare(queue, false, false, false, null);
			channel.basicPublish("", queue, null, message.toString().getBytes());
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
