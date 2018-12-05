package com.panduit.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Byounghyun An
 */
public class SerializationUtils {

	/**
	 * Serializes an Object to a byte array for storage/serialization.
	 * 
	 * @param obj
	 *            the object to serialize to bytes
	 * @return the byte[] with the converted Serializable
	 * @throws IOException
	 *             in case of an I/O error
	 */
	public static byte[] serialize(Serializable obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		return baos.toByteArray();
	}

	/**
	 * Deserializes a single Object from an array of bytes.
	 * 
	 * @param data
	 *            the serialized object, must not be null
	 * @return the deserialized object
	 * @throws IOException
	 *             in case of an I/O error
	 * @throws ClassNotFoundException
	 *             if the class cannot be located
	 */
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		return ois.readObject();
	}

}
