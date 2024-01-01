package com.bfu.javafxchatapp.client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {
	private ArrayList<Thread> threads;
	private Stage primaryStage;
	public static void main(String[] args){
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		threads = new ArrayList<Thread>();
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("JavaFX Chat Client");
		this.primaryStage.setScene(setLoginScene(primaryStage));
		this.primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		for (Thread thread: threads){
			thread.interrupt();
		}
	}

	public Scene setLoginScene(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bfu/javafxchatapp/ClientAuth.fxml"));
		Parent root = fxmlLoader.load();
		ClientAuthController controller = fxmlLoader.getController();
		controller.setClientApplication(this);
		return new Scene(root, 400, 300);
	};

	public Scene makeChatBox(ClientService clientService) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bfu/javafxchatapp/ClientChat.fxml"));
		Parent root = fxmlLoader.load();
		ClientController controller = fxmlLoader.getController();
		controller.setClientService(clientService);
		return new Scene(root, 400, 300);
	};

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
