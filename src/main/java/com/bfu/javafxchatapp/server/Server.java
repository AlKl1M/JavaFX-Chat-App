package com.bfu.javafxchatapp.server;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    private List<Socket> clients;
    private ArrayList<ClientThread> clientThreads;
    public ObservableList<String> serverLog;
    public ObservableList<String> clientNames;

    public Server(int port) {
        this.port = port;
        serverLog = FXCollections.observableArrayList();
        clientNames = FXCollections.observableArrayList();
        clients = new ArrayList<Socket>();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverLog = FXCollections.observableArrayList();
    }

    @Override
    public void run() {
        while (true) {
            listerForClient();
            acceptClientSocket();
        }
    }

    private void listerForClient() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serverLog.add("Listening for client");
            }
        });
    }

    private void acceptClientSocket() {
        try {
            final Socket clientSocket = serverSocket.accept();
            addClientSocket(clientSocket);
            logClientConnected(clientSocket);
            ClientThread clientThreadHolder = createClientThread(clientSocket);
            Thread clientThread = new Thread(clientThreadHolder);
            clientThread.setDaemon(true);
            startClientThread(clientThread);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addClientSocket(Socket clientSocket) {
        clients.add(clientSocket);
    }

    private void logClientConnected(Socket clientSocket) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serverLog.add("Client " + clientSocket.getRemoteSocketAddress() + " connected");
            }
        });
    }

    private ClientThread createClientThread(Socket clientSocket) {
        ClientThread clientThreadHolder = new ClientThread(clientSocket, this);
        clientThreads.add(clientThreadHolder);
        return clientThreadHolder;
    }

    private void startClientThread(Thread clientThread) {
        clientThread.start();
    }

    public void clientDisconnected(ClientThread client) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serverLog.add("Client " + client.getClientSocket().getRemoteSocketAddress() + " disconnected");
                clients.remove(clientThreads.indexOf(client));
                clientNames.remove(clientThreads.indexOf(client));
                clientThreads.remove(clientThreads.indexOf(client));
            }
        });
    }

    public void writeToAllSockets(String inputToServer) {
        for (ClientThread clientThread : clientThreads) {
            clientThread.writeToServer(inputToServer);
        }
    }
}
