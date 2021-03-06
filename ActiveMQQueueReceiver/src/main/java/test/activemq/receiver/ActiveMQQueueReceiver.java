package test.activemq.receiver;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.activemq.MQClient;

public class ActiveMQQueueReceiver {

	public static Logger LOGGER = LoggerFactory
			.getLogger(ActiveMQQueueReceiver.class);

	private String brokerURL;
	private String userName;
	private String password;

	public ActiveMQQueueReceiver(final String serverURL, final String userName,
			final String password) {
		this.brokerURL = serverURL;
		this.userName = userName;
		this.password = password;
	}

	public void startReceiving(final String queueName)
			throws JMSException {

		Connection connection = null;
		Session session = null;

		try {

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination queue = session.createQueue(queueName);

			MessageConsumer consumer = session.createConsumer(queue);

			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {

					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;
						System.out.println("message received from " + queueName);
						try {
							System.out.println("Message :"+ textMessage.getText());
						} catch (JMSException e) {
							
						}
					}

				}
			});
			
			System.in.read();
			consumer.close();

		} catch (IOException e) {
			LOGGER.error("error in input" + e);
		} finally {
			if (connection != null)
				connection.close();
			if (session != null)
				session.close();
		}
	}

	public String getServerURL() {
		return brokerURL;
	}

	public void setServerURL(String serverURL) {
		this.brokerURL = serverURL;
	}

}
