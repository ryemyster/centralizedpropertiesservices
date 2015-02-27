/**
 * 
 */
package com.properties.queueservices;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;

/**
 * @author ryanmcdonald
 */
public class PropertiesMessage implements MessageCreator {

	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesMessage.class);

	private volatile Object object;

	/**
	 * 
	 * @param message
	 */
	public PropertiesMessage(final Object message) {
		this.object = message;
	}

	/**
	 * 
	 */
	@Override
	public Message createMessage(final Session session) throws JMSException {

		ObjectMessage om = session.createObjectMessage((Serializable) object);
		logger.debug("Sending message: " + object.toString());
		return om;
	}

}
