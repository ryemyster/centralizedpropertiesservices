/**
 * 
 */
package com.properties.fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import com.properties.exception.PropertiesException;

/**
 * @author ryanmcdonald
 * 
 *         TODO: Store every instance property individually
 * 
 *         Read from directory
 * 
 *         Write by appending the instance as the file name
 * 
 *         USE UUID for filename and update instance_id as well
 */
public class FileServiceImpl implements FileService {

	@Value("#{properties[propertiesReadLocation]}")
	private String readLocation;

	@Value("#{properties[propertiesWriteLocation]}")
	private String writeLocation;

	/**
	 * 
	 */
	public FileServiceImpl() {
	}

	/**
	 * @return the readLocation
	 */
	public String getReadLocation() {
		return readLocation;
	}

	/**
	 * @param readLocation
	 *            the readLocation to set
	 */
	public void setReadLocation(final String readLocation) {
		this.readLocation = readLocation;
	}

	/**
	 * @return the writeLocation
	 */
	public String getWriteLocation() {
		return writeLocation;
	}

	/**
	 * @param writeLocation
	 *            the writeLocation to set
	 */
	public void setWriteLocation(final String writeLocation) {
		this.writeLocation = writeLocation;
	}

	/**
	 * 
	 * @param readlocation
	 * @param writeLocation
	 */
	public FileServiceImpl(final String readlocation, final String writeLocation) {
		setReadLocation(readlocation);
		setWriteLocation(writeLocation);
	}

	/**
	 * @throws PropertiesException
	 * 
	 */
	public synchronized String readSavedFile(final UUID fileID)
			throws PropertiesException {

		BufferedReader br = null;
		String inFile = "";
		FileReader fr = null;

		try {

			if (getReadLocation() == null) {
				throw new IOException("Failed to inject file path.");
			}

			String sCurrentLine;

			fr = new FileReader(readLocation);
			br = new BufferedReader(fr);

			while ((sCurrentLine = br.readLine()) != null) {
				inFile += sCurrentLine;
			}

			br.close();
			fr.close();

		} catch (IOException e) {
			throw new PropertiesException(e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}

				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				throw new PropertiesException(e);
			}
		}

		return inFile;
	}

	/**
	 * 
	 * @param fileInfo
	 * @throws PropertiesException
	 */
	public synchronized void saveFile(final UUID instanceId,
			final String fileInfo) throws PropertiesException {

		BufferedWriter bw = null;
		FileWriter fw = null;
		File file = null;

		try {

			if (getWriteLocation() == null) {
				throw new IOException("Failed to inject file path.");
			}

			file = new File(writeLocation);

			if (!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);

			bw.write(fileInfo);
			bw.close();
			fw.close();

		} catch (IOException e) {
			throw new PropertiesException(e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}

				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
			}
		}

	}
}
