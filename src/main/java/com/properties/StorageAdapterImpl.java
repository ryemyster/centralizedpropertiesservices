/**
 * 
 */
package com.properties;

import java.util.List;
import java.util.UUID;

import com.properties.database.DatabaseService;
import com.properties.exception.PropertiesException;
import com.properties.fileio.FileService;
import com.properties.queueservices.QueueServices;

/**
 * @author ryanmcdonald
 * 
 */
public class StorageAdapterImpl implements StorageAdapter {

	private QueueServices queueServices;
	private FileService fileService;
	private DatabaseService databaseService;

	private static boolean persistFile;
	private static boolean persistQueue;
	private static boolean persistDatabase;

	private static final String PREFIX = "instance_";

	/**
	 * 
	 */
	public StorageAdapterImpl() {
	}

	/**
	 * 
	 * @param persistFile
	 * @param persistQueue
	 * @param persistDatabase
	 */
	public StorageAdapterImpl(final boolean persistFile,
			final boolean persistQueue, final boolean persistDatabase) {
		setPersistDatabase(persistDatabase);
		setPersistFile(persistFile);
		setPersistQueue(persistQueue);
	}

	/**
	 * @return the databaseService
	 */
	public DatabaseService getDatabaseService() {
		return databaseService;
	}

	/**
	 * @param databaseService
	 *            the databaseService to set
	 */
	public void setDatabaseService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	/**
	 * @return the fileService
	 */
	public FileService getFileService() {
		return fileService;
	}

	/**
	 * @param fileService
	 *            the fileService to set
	 */
	public void setFileService(final FileService fileService) {
		this.fileService = fileService;
	}

	/**
	 * @return the queueServices
	 */
	public QueueServices getQueueServices() {
		return queueServices;
	}

	/**
	 * @param queueServices
	 *            the queueServices to set
	 */
	public void setQueueServices(QueueServices queueServices) {
		this.queueServices = queueServices;
	}

	/**
	 * 
	 * @param data
	 * @throws PropertiesException
	 * 
	 *             TODO: Provide multiple properties by Instance ID
	 */
	public void store(final UUID instanceId, final String data)
			throws PropertiesException {

		if (isPersistQueue()) {
			getQueueServices().generateMessage(data);
		}

		if (isPersistFile()) {
			getFileService().saveFile(instanceId, data);
		}

		if (isPersistDatabase()) {
			getDatabaseService().storePropertiesByInstance(
					PREFIX + instanceId.toString(), data);
		}

	}

	/**
	 * @throws PropertiesException
	 * 
	 */
	@Override
	public String get(final UUID instanceId) throws PropertiesException {
		return getFileService().readSavedFile(instanceId);
	}

	/**
	 * @return the persistFile
	 */
	public boolean isPersistFile() {
		return persistFile;
	}

	/**
	 * @param persistFile
	 *            the persistFile to set
	 */
	public void setPersistFile(boolean persistFile) {
		StorageAdapterImpl.persistFile = persistFile;
	}

	/**
	 * @return the persistQueue
	 */
	public boolean isPersistQueue() {
		return persistQueue;
	}

	/**
	 * @param persistQueue
	 *            the persistQueue to set
	 */
	public void setPersistQueue(boolean persistQueue) {
		StorageAdapterImpl.persistQueue = persistQueue;
	}

	/**
	 * @return the persistDatabase
	 */
	public boolean isPersistDatabase() {
		return persistDatabase;
	}

	/**
	 * @param persistDatabase
	 *            the persistDatabase to set
	 */
	public void setPersistDatabase(boolean persistDatabase) {
		StorageAdapterImpl.persistDatabase = persistDatabase;
	}

	/**
	 * TODO: EDIT THIS
	 */
	@Override
	public List<Object> getInstanceIds() throws PropertiesException {

		List<Object> properties = getDatabaseService().getPropertiesByInstance(
				PREFIX + "*");

		return properties;
	}

	@Override
	public boolean hasProperty(UUID instanceId) {
		return getDatabaseService().hasKey(instanceId.toString());
	}
}
