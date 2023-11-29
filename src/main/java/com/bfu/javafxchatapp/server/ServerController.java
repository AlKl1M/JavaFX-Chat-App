package com.bfu.javafxchatapp.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class ServerController {
    @FXML
    public GridPane rootPane;
    @FXML
    public Text portText;
    @FXML
    public Label errorLabel;
    @FXML
    public TextField portTextField;
    @FXML
    public Button portApprovalButton;
    private ServerApplication serverApplication;
    public void initialize() {
        portText.setFont(Font.font("Tahoma"));
    }

    @FXML
    void handlePortApprovalButton(ActionEvent event) {
        try {
            Server server = new Server(Integer.parseInt(portTextField.getText()));
            ServerService serverService = new ServerService(server);
            Thread serverThread = new Thread(serverService);
            serverThread.setName("Server Thread");
            serverThread.setDaemon(true);
            serverThread.start();
            serverApplication.threads.add(serverThread);
            serverApplication.getPrimaryStage().hide();
            serverApplication.getPrimaryStage().setScene(serverApplication.makeServerUIScene(serverService));
            serverApplication.getPrimaryStage().show();
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Invalid port number");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setServerApplication(ServerApplication serverApplication) {
        this.serverApplication = serverApplication;
    }
}
