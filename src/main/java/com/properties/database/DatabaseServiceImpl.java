/**
 * 
 */
package com.properties.database;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extensions.redis.CustomRedisTemplate;

/**
 * @author ryan mcdonald
 * 
 *         TODO: Implement Object and not String.
 */
public class DatabaseServiceImpl implements DatabaseService {

	/**
	 * 
	 */
	private CustomRedisTemplate<String, Object> customRedisTemplate;

	/**
	 * 
	 */
	public void storePropertiesByInstance(final String key, final String value) {

		Logger log = LoggerFactory.getLogger(DatabaseServiceImpl.class);
		log.debug("Here");
		customRedisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 
	 */
	public List<Object> getPropertiesByInstance(final String key) {

		List<Object> newList = new ArrayList<Object>();
		for (Object item : customRedisTemplate.keys(key)) {
			newList.add(customRedisTemplate.opsForValue().get(item).toString());
		}

		return newList;
	}

	/**
	 * 
	 */
	public boolean hasKey(final String key) {
		return customRedisTemplate.hasKey(key);
	}

	/**
	 * 
	 * @param key
	 */
	public void removeProperties(final String key) {
		customRedisTemplate.opsForValue().getOperations().delete(key);
	}

	/**
	 * @return the customRedisTemplate
	 */
	public CustomRedisTemplate<String, Object> getCustomRedisTemplate() {
		return customRedisTemplate;
	}

	/**
	 * @param customRedisTemplate
	 *            the customRedisTemplate to set
	 */
	public void setCustomRedisTemplate(
			CustomRedisTemplate<String, Object> customRedisTemplate) {
		this.customRedisTemplate = customRedisTemplate;
	}

}
