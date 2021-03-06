package test.activemq;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.activemq.receiver.ActiveMQQueueReceiver;

public class MQClient {

	public static Logger LOGGER = LoggerFactory.getLogger(MQClient.class);

	public static void main(String[] args) {

		ActiveMQQueueReceiver receiver = new ActiveMQQueueReceiver("tcp://localhost:61616", "admin", "admin");
		
		try {
			receiver.startReceiving("my queue");
		} catch (JMSException e) {
			LOGGER.error("could not send message beacuse of : " + e);
		}
	}

}
