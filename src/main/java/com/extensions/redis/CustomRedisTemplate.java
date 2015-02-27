/**
 * 
 */
package com.extensions.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author ryan mcdonald
 * 
 *         There is an issue with catching the Runtime Exception and bubbling it
 *         up to the Web Layer. This extension fixes the exception.
 * 
 *         TODO: Custom Error Handling must be implemented here for connection
 *         issues.
 */
public class CustomRedisTemplate<K, V> extends RedisTemplate<K, V> {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomRedisTemplate.class);

	private static final int ERROR_700 = 700;
	private static final int ERROR_701 = 701;
	private static final int ERROR_702 = 702;

	/**
	 * 
	 */
	@Override
	public <T> T execute(RedisCallback<T> action, boolean exposeConnection,
			boolean pipeline) {
		try {
			return super.execute(action, exposeConnection, pipeline);
		} catch (JedisConnectionException e) {
			logger.error(e.getMessage());
		} catch (RuntimeException e) {
			logger.error("@EXCEPTION: " + ERROR_700 + ":"
					+ e.getMessage().toUpperCase());
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public <T> T execute(SessionCallback<T> session) {
		try {
			return super.execute(session);
		} catch (JedisConnectionException e) {
			logger.error(e.getMessage());
		} catch (RuntimeException e) {
			logger.error("@EXCEPTION: " + ERROR_701 + ":"
					+ e.getMessage().toUpperCase());
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public List<Object> executePipelined(final SessionCallback<?> session,
			final RedisSerializer<?> resultSerializer) {
		try {
			return super.executePipelined(session, resultSerializer);
		} catch (JedisConnectionException e) {
			logger.error(e.getMessage());
		} catch (RuntimeException e) {
			logger.error("@EXCEPTION: " + ERROR_702 + ":"
					+ e.getMessage().toUpperCase());
		}
		return null;
	}

}
