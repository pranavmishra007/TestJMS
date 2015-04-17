package test.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.activemq.sender.ActiveMQQueueSender;

public class MQApplication {

	public static Logger LOGGER = LoggerFactory.getLogger(MQApplication.class);

	public static void main(String[] args) {

		ActiveMQQueueSender sender = new ActiveMQQueueSender("tcp://localhost:61616", "admin", "admin");

		try {
			while(true) {
				sender.sendMessage("my queue", "Hi, I am chatting through Active MQ");
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			LOGGER.error("could not send message beacuse of : " + e);
		}
	}

}
