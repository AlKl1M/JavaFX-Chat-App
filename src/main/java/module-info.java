module com.bfu.javafxchatapp {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.bfu.javafxchatapp.server;
    opens com.bfu.javafxchatapp.server to javafx.fxml;
}