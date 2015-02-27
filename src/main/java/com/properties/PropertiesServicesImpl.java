/**
 * 
 */
package com.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MultivaluedMap;

import org.springframework.stereotype.Service;

import com.properties.exception.PropertiesException;

/**
 * @author Ryan K. McDonald
 * @since 2014-05-02
 * @version 0.1
 * 
 *          TODO: Database Implementation - USING REDIS AND FILE FOR CACHE.
 *          Enhance later.
 * 
 *          TODO: AOP implementation
 */
@Service("propertiesService")
public class PropertiesServicesImpl implements PropertiesServices {

	private StorageAdapterFactory storageAdapterFactory;

	/**
	 * 
	 */
	public PropertiesServicesImpl() {
	}

	/**
	 * TODO Push all existing properties on to queue on startup
	 * 
	 * File or DB? For now use file on disk. In future make file save by
	 * timestamp and load last one. (Allow for override viw web ui)
	 * 
	 * DB - store in memory.
	 */
	public PropertiesServicesImpl(boolean persistFile) {
	}

	/**
	 * @return String
	 */
	public String getProperties(final UUID instanceId) {
		String jsonFileString = "";
		try {
			jsonFileString = getStorageAdapterFactory().instanceOf().get(
					instanceId);
		} catch (PropertiesException e) {
		}

		return jsonFileString;
	}

	/**
	 * 
	 * @param propertiesMap
	 */
	public void addProperties(final MultivaluedMap<String, String> propertiesMap) {
		// TODO: convert properties map to JSON
		// fileService.saveFile(properties);
	}

	/**
	 * @param instanceId
	 * @param properties
	 */
	public void addProperties(final UUID instanceId, final String properties) {
		try {
			getStorageAdapterFactory().instanceOf().store(instanceId,
					properties);
		} catch (PropertiesException e) {
		}
	}

	/**
	 * 
	 * @param storageAdapterFactory
	 */
	public void setStorageAdapterFactory(
			StorageAdapterFactory storageAdapterFactory) {
		this.storageAdapterFactory = storageAdapterFactory;
	}

	/**
	 * @return the storageAdapterFactory
	 */
	public StorageAdapterFactory getStorageAdapterFactory() {
		return storageAdapterFactory;
	}

	/**
	 * 
	 */
	@Override
	public List<Object> getInstanceIds() {
		List<Object> instanceIds = new ArrayList<Object>();

		try {
			instanceIds = getStorageAdapterFactory().instanceOf()
					.getInstanceIds();
		} catch (PropertiesException e) {
		}
		return instanceIds;
	}

	/**
	 * 
	 * @param key
	 */
	public boolean hasProperty(final UUID key) {
		return storageAdapterFactory.instanceOf().hasProperty(key);
	}

}
