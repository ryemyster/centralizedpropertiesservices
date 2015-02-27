/**
 * 
 */
package com.properties;

/**
 * @author ryanmcdonald
 * 
 */
public class StorageAdapterFactory {

	/**
	 * SINGLETON
	 * 
	 * TODO: UPDATE TO INJECT DATA SOURCES!!!!
	 */
	public volatile StorageAdapter adapter;

	/**
	 * 
	 */
	public StorageAdapterFactory() {
	}

	/**
	 *
	 */
	public synchronized StorageAdapter instanceOf() {

		// singleton
		synchronized (this) {
			if (adapter == null) {
				adapter = new StorageAdapterImpl();

				return adapter;
			} else {
				return adapter;
			}
		}
	}
}
