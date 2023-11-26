package com.bfu.javafxchatapp.server;

import com.bfu.javafxchatapp.client.Client;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread implements Runnable{
    private Socket clientSocket;
    private Server server;
    private BufferedReader incomingMessageReader;
    private PrintWriter outgoingMessageWriter;
    private String clientName;

    public ClientThread(Socket clientSocket, Server server) {
        try {
            this.setClientSocket(clientSocket);
            this.server = server;
            incomingMessageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outgoingMessageWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            this.clientName = getClientNameFromNetwork();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    server.clientNames.add(clientName + " - " + clientSocket.getRemoteSocketAddress());
                }
            });
            String inputToServer;
            while (true) {
                inputToServer = incomingMessageReader.readLine();
                server.writeToAllSockets(inputToServer);
            }
        } catch (SocketException e) {
            server.clientDisconnected(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToServer(String input) {
        outgoingMessageWriter.println(input);
    }

    public String getClientNameFromNetwork() throws IOException {
        return incomingMessageReader.readLine();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getClientName() {
        return clientName;
    }
}
