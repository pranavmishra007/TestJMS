package test.activemq.sender;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQQueueSender {

	private String brokerURL;
	private String userName;
	private String password;

	public ActiveMQQueueSender(final String serverURL, final String userName ,final String password) {
		this.brokerURL = serverURL;
		this.userName = userName;
		this.password = password;
	}

	public void sendMessage(final String queueName, final String textMessage)
			throws JMSException {

		Connection connection = null;
		Session session = null;

		try {
			
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination queue = session.createQueue(queueName);

			MessageProducer producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			TextMessage message = session.createTextMessage(textMessage);
			producer.send(message);
			
			System.out.println("message sent to " +queueName);
			
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
