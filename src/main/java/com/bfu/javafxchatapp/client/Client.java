package com.bfu.javafxchatapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client {
	private Socket clientSocket;
	private BufferedReader serverToClientReader;
	private PrintWriter clientToServerWriter;
	private String name;
	public ObservableList<String> chatLog;

	public Client(String hostName, int portNumber, String name) throws UnknownHostException, IOException {
			clientSocket = new Socket(hostName, portNumber);
			serverToClientReader = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			clientToServerWriter = new PrintWriter(
					clientSocket.getOutputStream(), true);
			chatLog = FXCollections.observableArrayList();
			this.name = name;
			clientToServerWriter.println(name);
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public BufferedReader getServerToClientReader() {
		return serverToClientReader;
	}

	public void setServerToClientReader(BufferedReader serverToClientReader) {
		this.serverToClientReader = serverToClientReader;
	}

	public PrintWriter getClientToServerWriter() {
		return clientToServerWriter;
	}

	public void setClientToServerWriter(PrintWriter clientToServerWriter) {
		this.clientToServerWriter = clientToServerWriter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
