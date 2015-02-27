/**
 * 
 */
package com.properties.queueservices;

import com.properties.exception.PropertiesException;

/**
 * @author ryanmcdonald
 * 
 */
public interface QueueServices {

	public void generateMessage(final Object message)
			throws PropertiesException;
}
