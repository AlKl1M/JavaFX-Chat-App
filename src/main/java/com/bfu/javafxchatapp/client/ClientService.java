package com.bfu.javafxchatapp.client;

import javafx.application.Platform;

import java.io.IOException;
import java.net.SocketException;

public class ClientService implements Runnable {
    private Client client;

    public ClientService(Client client) {
        this.client = client;
    }

    public void writeToServer(String input) {
        client.getClientToServerWriter().println(client.getName() + " : " + input);
    }

    public void run() {
        while (true) {
            try {
                final String inputFromServer = client.getServerToClientReader().readLine();
                Platform.runLater(() -> {
                    handleInputFromServer(inputFromServer);
                });
            } catch (SocketException e) {
                handleServerError();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void handleInputFromServer(String input) {
        client.chatLog.add(input);
    }
    public void handleServerError() {
        client.chatLog.add("Error in server");
    }

    public Client getClient() {
        return client;
    }
}
