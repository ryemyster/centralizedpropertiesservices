/**
 * 
 */
package com.properties;

import java.util.List;
import java.util.UUID;

import com.properties.exception.PropertiesException;

/**
 * @author ryanmcdonald
 * 
 */
public interface StorageAdapter {

	/**
	 * 
	 * @param instanceID
	 * @param data
	 * @throws PropertiesException
	 */
	public void store(final UUID instanceID, final String data)
			throws PropertiesException;

	/**
	 * 
	 * @return String
	 * @throws PropertiesException
	 */
	public String get(final UUID instanceId) throws PropertiesException;

	/**
	 * 
	 * @param value
	 */
	public void setPersistFile(final boolean value);

	/**
	 * 
	 * @param value
	 */
	public void setPersistQueue(final boolean value);

	/**
	 * 
	 * @param value
	 */
	public void setPersistDatabase(final boolean value);

	/**
	 * 
	 */
	public List<Object> getInstanceIds() throws PropertiesException;

	/**
	 * 
	 */
	public boolean hasProperty(final UUID instanceId);
}
