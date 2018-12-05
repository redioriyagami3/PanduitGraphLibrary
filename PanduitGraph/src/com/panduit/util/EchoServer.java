package com.panduit.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Byounghyun An
 */
public class EchoServer {

	private ServerSocket server;

	public EchoServer(int port) throws IOException {
		this.server = new ServerSocket();
		this.server.setReuseAddress(true);
		this.server.bind(new InetSocketAddress(port));
	}

	public void start() throws IOException, ClassNotFoundException {
		Socket socket = server.accept();
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		oos.writeObject(ois.readObject());

		ois.close();
		oos.close();
		socket.close();
	}

	public void stop() throws IOException {
		server.close();
	}
}
