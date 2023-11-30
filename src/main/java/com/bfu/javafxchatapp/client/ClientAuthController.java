package com.bfu.javafxchatapp.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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

    public Button getApprovalButton() {
        return approvalButton;
    }

    public TextField getPortNumberField() {
        return portNumberField;
    }

    public TextField getHostNameField() {
        return hostNameField;
    }

    public TextField getUserNicknameField() {
        return userNicknameField;
    }

    public GridPane getRootPane() {
        return rootPane;
    }

    public void approveLogin(ActionEvent event) {};

    public String getUserNickname() {
        return this.userNicknameField.getText();
    };

    public String getHostName() {
        return this.hostNameField.getText();
    };

    public String getPortNumber() {
        return this.portNumberField.getText();
    }
}
