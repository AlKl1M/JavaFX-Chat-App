package com.bfu.javafxchatapp.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private List<Socket> clients;
    private ArrayList<ClientThread> clientThreads;
    public ObservableList<String> serverLog;
    public ObservableList<String> clientNames;

    public Server(int port) throws IOException {
        this.port = port;
        serverLog = FXCollections.observableArrayList();
        clientNames = FXCollections.observableArrayList();
        clients = new ArrayList<Socket>();
        clientThreads = new ArrayList<ClientThread>();
        serverSocket = new ServerSocket(port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public List<Socket> getClients() {
        return clients;
    }

    public void setClients(List<Socket> clients) {
        this.clients = clients;
    }

    public ArrayList<ClientThread> getClientThreads() {
        return clientThreads;
    }

    public void setClientThreads(ArrayList<ClientThread> clientThreads) {
        this.clientThreads = clientThreads;
    }

    @Override
    public String toString() {
        return "Server{" +
                "port=" + port +
                ", serverSocket=" + serverSocket +
                ", clients=" + clients +
                ", clientThreads=" + clientThreads +
                ", serverLog=" + serverLog +
                ", clientNames=" + clientNames +
                '}';
    }
}
