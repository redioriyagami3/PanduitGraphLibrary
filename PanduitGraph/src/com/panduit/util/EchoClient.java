package com.panduit.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Byounghyun An
 */
public class EchoClient {

	public Object echo(int port, Object requestObj) throws IOException, ClassNotFoundException {
		InetAddress host = InetAddress.getLocalHost();
		Socket client = new Socket(host.getHostName(), port);

		ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(client.getInputStream());

		oos.writeObject(requestObj);
		Object responseObj = ois.readObject();

		ois.close();
		oos.close();
		client.close();

		return responseObj;
	}

}
