module com.example.javafxapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.javafxapi to javafx.fxml;
    exports com.example.javafxapi;
}