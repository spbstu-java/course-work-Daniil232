package com.example.coursework.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_Openlab1;

    @FXML
    private Button button_OpenLab2;

    @FXML
    private Button button_OpenLab3;

    @FXML
    private Button button_OpenLab4;

    private final String[] labFiles = {
            "lab1-view.fxml",
            "lab2-view.fxml",
            "lab3-view.fxml",
            "lab4-view.fxml"
    };

    @FXML
    void initialize() {
        System.out.println("MainController инициализирован!");

        // Добавляем обработчики
        button_Openlab1.setOnAction(event -> openLab1(event));
        button_OpenLab2.setOnAction(event -> openLab2(event));
        button_OpenLab3.setOnAction(event -> openLab3(event));
        button_OpenLab4.setOnAction(event -> openLab4(event));
    }

    @FXML
    private void openLab1(ActionEvent event) {
        System.out.println("Нажата кнопка Лабораторная 1");
        openLab(event, 0);
    }

    @FXML
    private void openLab2(ActionEvent event) {
        System.out.println("Нажата кнопка Лабораторная 2");
        openLab(event, 1);
    }

    @FXML
    private void openLab3(ActionEvent event) {
        System.out.println("Нажата кнопка Лабораторная 3");
        openLab(event, 2);
    }

    @FXML
    private void openLab4(ActionEvent event) {
        System.out.println("Нажата кнопка Лабораторная 4");
        openLab(event, 3);
    }

    private void openLab(ActionEvent event, int labNumber) {
        try {
            System.out.println("Попытка загрузить: " + labFiles[labNumber]);

            String fxmlPath = "/com/example/coursework/" + labFiles[labNumber];
            URL resourceUrl = getClass().getResource(fxmlPath);

            if (resourceUrl == null) {
                System.out.println("Файл не найден: " + fxmlPath);
                return;
            }

            Parent root = FXMLLoader.load(resourceUrl);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 700, 650));
            stage.setTitle("Лабораторная работа " + (labNumber + 1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка загрузки: " + labFiles[labNumber]);
        }
    }
}