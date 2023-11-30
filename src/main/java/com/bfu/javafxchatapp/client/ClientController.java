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

    public TextField getInput() {
        return input;
    }
}
