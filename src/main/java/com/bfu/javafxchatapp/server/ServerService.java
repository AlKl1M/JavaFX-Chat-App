package com.bfu.javafxchatapp.server;

import javafx.application.Platform;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService implements Runnable {
    private final Server server;

    public ServerService(Server server) {
        this.server = server;
    }

    public void startServer() {
        try {
            server.setServerSocket(new ServerSocket(server.getPort()));
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid port");
        } catch (SecurityException e) {
            System.err.println("Insufficient permissions to open the socket");
        } catch (IOException e) {
            System.err.println("Input/output error occurred while opening the socket");
        }
        server.serverLog = FXCollections.observableArrayList();
    }

    @Override
    public void run() {
        while (true) {
            listerForClient();
            acceptClientSocket();
        }
    }

    private void listerForClient() {
        Platform.runLater(() -> {
            server.serverLog.add("Listening for client");
        });
    }

    private void acceptClientSocket() {
        try {
            final Socket clientSocket = server.getServerSocket().accept();
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
        server.getClients().add(clientSocket);
    }

    private void logClientConnected(Socket clientSocket) {
        Platform.runLater(() -> {
            server.serverLog.add("Client " + clientSocket.getRemoteSocketAddress() + " connected");
        });
    }

    private ClientThread createClientThread(Socket clientSocket) {
        ClientThread clientThreadHolder = new ClientThread(clientSocket, this.server);
        server.getClientThreads().add(clientThreadHolder);
        return clientThreadHolder;
    }

    private void startClientThread(Thread clientThread) {
        clientThread.start();
    }

    public void clientDisconnected(ClientThread client) {
        Platform.runLater(() ->
                {
                    server.serverLog.add("Client " + client.getClientSocket().getRemoteSocketAddress() + " disconnected");
                    server.getClients().remove(server.getClientThreads().indexOf(client));
                    server.clientNames.remove(server.getClientThreads().indexOf(client));
                    server.getClientThreads().remove(server.getClientThreads().indexOf(client));
                }
        );
    }

    public void writeToAllSockets(String inputToServer) {
        for (ClientThread clientThread : server.getClientThreads()) {
            clientThread.writeToServer(inputToServer);
        }
    }

    public Server getServer() {
        return server;
    }
}
