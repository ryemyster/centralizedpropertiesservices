/**
 * 
 */
package com.properties.database;

import java.util.List;

/**
 * @author ryanmcdonald
 * 
 */
public interface DatabaseService {

	/**
	 * @param properties
	 */
	public void storePropertiesByInstance(final String key, final String value);

	/**
	 * 
	 */
	public List<Object> getPropertiesByInstance(final String key);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasKey(final String key);
}
