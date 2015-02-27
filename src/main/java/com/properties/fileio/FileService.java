package com.properties.fileio;

import java.util.UUID;

import com.properties.exception.PropertiesException;

public interface FileService {
	public String readSavedFile(final UUID fileId) throws PropertiesException;

	public void saveFile(final UUID instanceId, final String fileInfo)
			throws PropertiesException;
}
