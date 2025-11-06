package com.example.coursework.controller;

import com.example.coursework.lab2.TestService;
import com.example.coursework.lab2.MethodExecutor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.io.IOException;

public class Lab2Controller {

    @FXML
    private RadioButton publicMethodsRadio;

    @FXML
    private RadioButton privateMethodsRadio;

    @FXML
    private RadioButton protectedMethodsRadio;

    @FXML
    private RadioButton allMethodsRadio;

    @FXML
    private Button executeButton;

    @FXML
    private TextArea outputArea;

    @FXML
    private Button clearButton;

    private ToggleGroup methodGroup;
    private TestService testService;
    private MethodExecutor methodExecutor;

    @FXML
    void initialize() {
        System.out.println("Lab2Controller инициализирован!");

        // Инициализация сервисов
        testService = new TestService(outputArea);
        methodExecutor = new MethodExecutor(outputArea);

        // Настройка RadioButton группы
        methodGroup = new ToggleGroup();
        publicMethodsRadio.setToggleGroup(methodGroup);
        privateMethodsRadio.setToggleGroup(methodGroup);
        protectedMethodsRadio.setToggleGroup(methodGroup);
        allMethodsRadio.setToggleGroup(methodGroup);

        // Выбираем все методы по умолчанию
        allMethodsRadio.setSelected(true);

        // Обработчики кнопок
        executeButton.setOnAction(event -> executeMethods());
        clearButton.setOnAction(event -> clearOutput());

        outputArea.appendText("Лабораторная работа 2: Аннотации и рефлексия\n");
        outputArea.appendText("Выберите тип методов для выполнения и нажмите 'Выполнить методы'\n\n");
    }

    @FXML
    private void executeMethods() {
        outputArea.clear();

        try {
            if (publicMethodsRadio.isSelected()) {
                outputArea.appendText("=== ВЫПОЛНЕНИЕ PUBLIC МЕТОДОВ ===\n");
                methodExecutor.invokePublicMethods(testService);
            } else if (privateMethodsRadio.isSelected()) {
                outputArea.appendText("=== ВЫПОЛНЕНИЕ PRIVATE МЕТОДОВ ===\n");
                methodExecutor.invokePrivateMethods(testService);
            } else if (protectedMethodsRadio.isSelected()) {
                outputArea.appendText("=== ВЫПОЛНЕНИЕ PROTECTED МЕТОДОВ ===\n");
                methodExecutor.invokeProtectedMethods(testService);
            } else if (allMethodsRadio.isSelected()) {
                outputArea.appendText("=== ВЫПОЛНЕНИЕ ВСЕХ МЕТОДОВ ===\n");
                methodExecutor.invokeAllMethods(testService);
            }

            outputArea.appendText("\n=== ВЫПОЛНЕНИЕ ЗАВЕРШЕНО ===\n");

        } catch (Exception e) {
            outputArea.appendText("ОШИБКА: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    @FXML
    private void clearOutput() {
        outputArea.clear();
        outputArea.appendText("Очищено. Выберите тип методов для выполнения.\n");
    }

    @FXML
    private void goBackToMain() {
        try {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("/com/example/coursework/courseworkMainMenu.fxml"));
            Stage stage = (Stage) outputArea.getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 600, 600));
            stage.setTitle("Главное меню");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}