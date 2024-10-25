module ClothingStoreManagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires jdk.jdi;
    requires mysql.connector.j;


    opens org.example.controller.clothingstoreFX to javafx.fxml;
    exports org.example.controller.clothingstoreFX;
    exports db;
    opens db to javafx.fxml;
}