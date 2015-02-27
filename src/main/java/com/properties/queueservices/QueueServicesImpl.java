/**
 * 
 */
package com.properties.queueservices;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import com.properties.exception.PropertiesException;

/**
 * @author ryanmcdonald
 * 
 */
public class QueueServicesImpl implements QueueServices {

	protected static final String MESSAGE_COUNT = "messageCount";

	private JmsTemplate jmsTemplate;

	/**
	 * 
	 * @param message
	 * @throws PropertiesException
	 */
	public void generateMessage(final Object message)
			throws PropertiesException {

		try {
			jmsTemplate.send(new PropertiesMessage(message));
		} catch (JmsException ex) {
			throw new PropertiesException(ex);
		}
	}

	/**
	 * @return the jmsTemplate
	 */
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	/**
	 * @param jmsTemplate
	 *            the jmsTemplate to set
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
