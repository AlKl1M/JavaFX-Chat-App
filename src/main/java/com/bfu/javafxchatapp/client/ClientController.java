package com.bfu.javafxchatapp.client;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClientController {
    @FXML
    private ListView<String> chatbox;

    @FXML
    private TextField input;

    public ListView<String> getChatbox() {
        return chatbox;
    }

    public void setClientService(ClientService clientService) {
        this.getChatbox().setItems(clientService.getClient().chatLog);
        this.getInput().setOnAction(event -> {
            clientService.writeToServer(getInput().getText());
            getInput().clear();
        });
    }

    public TextField getInput() {
        return input;
    }
}
