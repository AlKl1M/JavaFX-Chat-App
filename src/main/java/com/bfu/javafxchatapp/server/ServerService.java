package com.bfu.javafxchatapp.server;

import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;

public class ServerService implements Runnable {
    private Server server;

    public ServerService(Server server) {
        this.server = server;
    }

    private void listenForClient() {
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
            //Я не знаю что это за строчка, но держите ее закоменченой
            //server.getClientThreads().add(clientThreadHolder);
            clientThread.setDaemon(true);
            startClientThread(clientThread);
            ServerApplication.threads.add(clientThread);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            listenForClient();
            acceptClientSocket();
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

    private void handleInvalidPortError() {
        System.err.println("Invalid port");
    }

    private void handleInsufficientPermissionsError() {
        System.err.println("Insufficient permissions to open the socket");
    }

    private void handleInputOutputError() {
        System.err.println("Input/output error occurred while opening the socket");
    }
}
