package com.panduit.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Byounghyun An
 */
public class FileUtils {

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	/**
	 * Writes a byte array to a file creating the file if it does not exist.
	 *
	 * @param file
	 *            the file to write to
	 * @param data
	 *            the content to write to the file
	 * @throws IOException
	 *             in case of an I/O error
	 */
	public static void writeByteArrayToFile(File file, byte[] data) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory.");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file + "' cannot be written to.");
			}
		} else {
			final File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.mkdirs() && !parent.isDirectory()) {
					throw new IOException("Directory '" + parent + "' could not be created.");
				}
			}
		}
		
		OutputStream os = new FileOutputStream(file);
		os.write(data);
		os.close();
	}

	/**
	 * Reads the contents of a file into a byte array. The file is always closed.
	 *
	 * @param file
	 *            the file to read, must not be null
	 * @return the file contents, never null
	 * @throws IOException
	 *             in case of an I/O error
	 */
	public static byte[] readFileToByteArray(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory.");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read.");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist.");
		}
		
		InputStream is = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int n;
		
		while ((n = is.read(buffer)) != -1) {
			baos.write(buffer, 0, n);
		}
		
		is.close();
		
		return baos.toByteArray();
	}

}
