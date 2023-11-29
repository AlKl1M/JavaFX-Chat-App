package com.bfu.javafxchatapp.server;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread implements Runnable{
    private Socket clientSocket;
    private ServerService serverService;
    private BufferedReader incomingMessageReader;
    private PrintWriter outgoingMessageWriter;
    private String clientName;

    public ClientThread(Socket clientSocket, Server server) {
        try {
            this.clientSocket = clientSocket;
            this.serverService = new ServerService(server);
            incomingMessageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outgoingMessageWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            handleInputOutputError();
        }
    }

    @Override
    public void run() {
        try {
            this.clientName = getClientNameFromNetwork();
            addClientNameToList();
            listenForIncomingMessages();
        } catch (SocketException e) {
            serverService.clientDisconnected(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addClientNameToList() {
        Platform.runLater(() -> {
            serverService.getServer().clientNames.add(clientName + " - " + clientSocket.getRemoteSocketAddress());
        });
    }

    private void listenForIncomingMessages() throws IOException {
        String inputToServer;
        while (true) {
            inputToServer = incomingMessageReader.readLine();
            serverService.writeToAllSockets(inputToServer);
        }
    }

    public void writeToServer(String input) {
        outgoingMessageWriter.println(input);
    }

    public String getClientNameFromNetwork() throws IOException {
        return incomingMessageReader.readLine();
    }

    private void handleInputOutputError() {
        System.err.println("Input/output error occurred while opening the socket");
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getClientName() {
        return clientName;
    }
}
