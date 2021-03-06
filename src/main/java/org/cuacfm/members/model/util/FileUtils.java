/**
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cuacfm.members.model.util;

import static org.apache.commons.io.FileUtils.forceMkdir;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * The Class FileUtils.
 */
public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * Instantiates a new file utils.
	 */
	private FileUtils() {
		super();
	}

	/**
	 * Creates the folder if no exist.
	 *
	 * @param directoryName the directory name
	 */
	public static void createFolderIfNoExist(String directoryName) {
		try {
			forceMkdir(new File(directoryName));
		} catch (IOException e) {
			logger.error("createFolderIfNoExist: ", e);
		}
	}

	/**
	 * Download File.
	 *
	 * @param path the path
	 * @param file the file
	 * @param mediaType the media type
	 * @return the response entity
	 */
	public static ResponseEntity<byte[]> downloadFile(String path, String file, MediaType mediaType) {
		Path pathAux = Paths.get(path + file);
		byte[] contents = null;
		try {
			contents = Files.readAllBytes(pathAux);
		} catch (IOException e) {
			logger.error("downloadFile: ", e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{file}").buildAndExpand(file).toUri());
		headers.add("content-disposition", "attachment; filename=" + file + ";");
		return new ResponseEntity<>(contents, headers, HttpStatus.OK);
	}

	/**
	 * List files for folder.
	 *
	 * @param folder the folder
	 * @return the list
	 */
	public static List<File> listFilesForFolder(final File folder) {
		List<File> files = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				files.add(fileEntry);
			}
		}
		return files;
	}

	/**
	 * List files for folder to list string.
	 *
	 * @param folder the folder
	 * @return the list
	 */
	public static List<String> listFilesForFolderToListString(final File folder) {
		List<String> files = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolderToListString(fileEntry);
			} else {
				files.add(fileEntry.getName());
			}
		}
		return files;
	}

	/**
	 * Split.
	 *
	 * @param camp the camp
	 * @param maxLength the max length
	 * @return the string
	 */
	public static String split(String camp, int maxLength) {
		String campAux = camp;
		if (campAux != null && campAux.length() > maxLength) {
			return campAux.trim().substring(0, maxLength);
		} else {
			return campAux;
		}
	}

	/**
	 * Change value, if newValue different of null.
	 *
	 * @param oldValue the old value
	 * @param newValue the new value
	 * @return the string
	 */
	public static String changeValue(String oldValue, String newValue) {
		if (newValue != null && !newValue.isEmpty()) {
			return newValue.trim();
		} else {
			return oldValue;
		}
	}

	/**
	 * Change value, if newValue different of null.
	 *
	 * @param oldValue the old value
	 * @param newValue the new value
	 * @return the object
	 */
	public static Object changeValue(Object oldValue, Object newValue) {
		if (newValue != null) {
			return newValue;
		} else {
			return oldValue;
		}
	}

	/**
	 * Gets the boolean of String value
	 *
	 * @param value the value
	 * @return the boolean
	 */
	public static Boolean getBoolean(String value) {
		if (value != null && !value.isEmpty()) {
			return "Si".equalsIgnoreCase(value.trim()) ? true : false;
		}
		return null;
	}

	/**
	 * Gets the float.
	 *
	 * @param value the value
	 * @return the float
	 */
	public static Float getFloat(String value) {
		if (value != null && !value.isEmpty()) {
			return Float.valueOf(value);
		}
		return null;
	}

	/**
	 * Gets the integer.
	 *
	 * @param value the value
	 * @return the integer
	 */
	public static Integer getInteger(String value) {
		if (value != null && !value.isEmpty()) {
			return Integer.valueOf(value);
		}
		return null;
	}

	/**
	 * Gets the file.
	 *
	 * @param path the path
	 * @return the file
	 */
	public static InputStream getFile(String path) {
		return FileUtils.class.getClassLoader().getResourceAsStream(path);
	}
}
