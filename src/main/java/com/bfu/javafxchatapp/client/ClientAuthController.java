package com.bfu.javafxchatapp.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ClientAuthController {
    @FXML
    private GridPane rootPane;

    @FXML
    private TextField userNicknameField;

    @FXML
    private TextField hostNameField;

    @FXML
    private TextField portNumberField;

    @FXML
    private Button approvalButton;
    private ClientApplication clientApplication;

    @FXML
    void handleApprovalButton(ActionEvent event) {
        try {
            Client client = new Client(getHostName(), Integer.parseInt(getPortNumber()), getUserNickname());
            ClientService clientService = new ClientService(client);
            Thread clientThread = new Thread(clientService);
            clientThread.setDaemon(true);
            clientThread.start();
            showServerUI(clientService);
        } catch(NumberFormatException | IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void showServerUI(ClientService clientService) throws IOException {
        Scene clientUIScene = clientApplication.makeChatBox(clientService);
        clientApplication.getPrimaryStage().setScene(clientUIScene);
        clientApplication.getPrimaryStage().show();
    }

    public String getUserNickname() {
        return this.userNicknameField.getText();
    };

    public String getHostName() {
        return this.hostNameField.getText();
    };

    public String getPortNumber() {
        return this.portNumberField.getText();
    }

    public void setClientApplication(ClientApplication clientApplication) {
        this.clientApplication = clientApplication;
    }
}
