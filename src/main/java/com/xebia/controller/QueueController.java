package com.xebia.controller;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.domain.Message;
import com.xebia.domain.Response;

@RestController
public class QueueController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueueController.class);
	private static final String JMS_CONNECTION_FACTORY = "java:comp/env/jmsConnectionFactory";
	private static final String QUEUE = "java:comp/env/jndi/myQueue";
	private static final String SUCCESS = "Sent";
	private static final String FAILED = "Failed";

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public Response sendMessage(@RequestBody Message message) {
		try {
			sendMessageToWmqQueue(message);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred", e);
			return new Response(FAILED);
		}
		return new Response(SUCCESS);
	}

	private void sendMessageToWmqQueue(final Message message) throws Exception {
		ConnectionFactory cf1 = (ConnectionFactory) new InitialContext().lookup(JMS_CONNECTION_FACTORY);
		Queue queue = (Queue) new InitialContext().lookup(QUEUE);
		Connection con = cf1.createConnection();
		con.start();
		Session sessionSender = con.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
		MessageProducer send = sessionSender.createProducer(queue);
		TextMessage msg = sessionSender.createTextMessage(message.getMessage());
		send.send(msg);
		if (con != null)
			con.close();
	}
}