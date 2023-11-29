module com.bfu.javafxchatapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bfu.javafxchatapp.server to javafx.fxml;
    exports com.bfu.javafxchatapp.server;

    opens com.bfu.javafxchatapp.client to javafx.fxml;
    exports com.bfu.javafxchatapp.client;
}