package com.bfu.javafxchatapp.server;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class ServerController {
    @FXML
    public GridPane rootPane;
    @FXML
    public Label logLabel;
    @FXML
    public ListView<String> logView;
    @FXML
    public Label clientLabel;
    @FXML
    public ListView<String> clientView;
    public void setServerService(ServerService serverService) {
        logView.setItems(serverService.getServer().serverLog);
        clientView.setItems(serverService.getServer().clientNames);
    }
}
