module com.bfu.javafxchatapp {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.bfu.javafxchatapp to javafx.fxml;
    exports com.bfu.javafxchatapp;
}