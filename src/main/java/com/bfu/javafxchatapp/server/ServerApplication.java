package com.bfu.javafxchatapp.server;

import com.bfu.javafxchatapp.server.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ServerApplication extends Application {
    public static ArrayList<Thread> threads;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        threads = new ArrayList<>();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JavaFX Chat Server");
        this.primaryStage.setScene(makePortUIScene());
        this.primaryStage.show();
    }

    public Scene makePortUIScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bfu/javafxchatapp/ServerAuth.fxml"));
        Parent root = fxmlLoader.load();
        ServerAuthController controller = fxmlLoader.getController();
        controller.setServerApplication(this);
        return new Scene(root, 400, 300);
    }

    public Scene makeServerUIScene(ServerService serverService) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bfu/javafxchatapp/Server.fxml"));
        Parent root = fxmlLoader.load();
        ServerController controller = fxmlLoader.getController();
        controller.setServerService(serverService);
        return new Scene(root, 400, 300);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
