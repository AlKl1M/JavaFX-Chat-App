package com.bfu.javafxchatapp.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
public class ServerAuthController {
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
            Server server = createServer();
            ServerService serverService = createServerService(server);
            Thread serverThread = createAndStartServerThread(serverService);
            addServerThreadToList(serverThread);
            hidePrimaryStage();
            showServerUI(serverService);
        } catch (IllegalArgumentException e) {
            setErrorLabelText("Invalid port number");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Server createServer() throws IOException {
        int port = Integer.parseInt(portTextField.getText());
        return new Server(port);
    }
    private ServerService createServerService(Server server) {
        return new ServerService(server);
    }
    private Thread createAndStartServerThread(ServerService serverService) {
        Thread serverThread = new Thread(serverService);
        serverThread.setName("Server Thread");
        serverThread.setDaemon(true);
        serverThread.start();
        return serverThread;
    }
    private void addServerThreadToList(Thread serverThread) {
        serverApplication.threads.add(serverThread);
    }
    private void hidePrimaryStage() {
        serverApplication.getPrimaryStage().hide();
    }
    private void showServerUI(ServerService serverService) throws IOException {
        Scene serverUIScene = serverApplication.makeServerUIScene(serverService);
        serverApplication.getPrimaryStage().setScene(serverUIScene);
        serverApplication.getPrimaryStage().show();
    }

    private void setErrorLabelText(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    public void setServerApplication(ServerApplication serverApplication) {
        this.serverApplication = serverApplication;
    }
}
