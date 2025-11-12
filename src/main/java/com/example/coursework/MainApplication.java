package com.example.coursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("courseworkMainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 650); // Увеличиваем размер окна
        stage.setTitle("Курсовая работа - Баранов Даниил Олегович");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(650);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}