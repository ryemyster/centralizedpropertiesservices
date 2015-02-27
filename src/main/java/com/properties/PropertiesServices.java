/**
 * 
 */
package com.properties;

import java.util.List;
import java.util.UUID;

/**
 * @author ryanmcdonald
 * 
 */
public interface PropertiesServices {
	/**
	 * 
	 * @param properties
	 */
	public void addProperties(final UUID instanceID, final String properties);

	/**
	 * 
	 * @param instanceId
	 * @return String
	 */
	public String getProperties(final UUID instanceId);

	/**
	 * 
	 */
	public List<Object> getInstanceIds();

	/**
	 * 
	 */
	public boolean hasProperty(final UUID key);
}
