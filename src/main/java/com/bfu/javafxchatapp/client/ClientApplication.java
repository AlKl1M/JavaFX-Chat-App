package com.bfu.javafxchatapp.client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {
	private ArrayList<Thread> threads;
	public static void main(String[] args){
		launch();
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		for (Thread thread: threads){
			thread.interrupt();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		threads = new ArrayList<Thread>();
		primaryStage.setTitle("JavaFX Chat Client");
		primaryStage.setScene(setLoginScene(primaryStage));
		primaryStage.show();
	}

	public Scene setLoginScene(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bfu/javafxchatapp/ClientAuth.fxml"));
		Parent root = fxmlLoader.load();
		ClientAuthController controller = fxmlLoader.getController();
		controller.getApprovalButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent Event) {
				Client client;
				try {
					client = new Client(controller.getHostName(), Integer
							.parseInt(controller.getPortNumber()), controller.getUserNickname());
					Thread clientThread = new Thread(client);
					clientThread.setDaemon(true);
					clientThread.start();
					threads.add(clientThread);

					/* Change the scene of the primaryStage */
					primaryStage.close();
					primaryStage.setScene(makeChatBox(client));
					primaryStage.show();
				}
				catch(NumberFormatException | IOException e){
					System.out.println(e.getMessage());
				}
			}
		});
		return new Scene(root, 400, 300);
	};

	public Scene makeChatBox(Client client) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bfu/javafxchatapp/ClientChat.fxml"));
		Parent root = fxmlLoader.load();
		ClientController controller = fxmlLoader.getController();
		controller.getChatbox().setItems(client.chatLog);
		controller.getInput().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				client.writeToServer(controller.getInput().getText());
				controller.getInput().clear();
			}
		});

		return new Scene(root, 400, 300);
	};
}
