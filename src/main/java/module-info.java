module com.example.coursework {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.coursework to javafx.fxml;
    exports com.example.coursework;
    exports com.example.coursework.controller;
    opens com.example.coursework.controller to javafx.fxml;
    exports com.example.coursework.lab1;
    opens com.example.coursework.lab1 to javafx.fxml;
}