package com.example.coursework.controller;

import com.example.coursework.lab1.Hero;
import com.example.coursework.lab1.WalkStrategy;
import com.example.coursework.lab1.HorseRideStrategy;
import com.example.coursework.lab1.FlyStrategy;
import com.example.coursework.lab1.TeleportStrategy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import java.io.IOException;

public class Lab1Controller {

    @FXML
    private TextField heroNameField;

    @FXML
    private ComboBox<String> startLocationComboBox;

    @FXML
    private ComboBox<String> strategyComboBox;

    @FXML
    private ComboBox<String> targetLocationComboBox;

    @FXML
    private Button moveButton;

    @FXML
    private Button changeStrategyButton;

    @FXML
    private Button createHeroButton;

    @FXML
    private TextArea outputArea;

    @FXML
    private Label currentLocationLabel;

    @FXML
    private Label currentStrategyLabel;

    private Hero hero;
    private final String[] locations = {"Замок", "Лес", "Гора", "Деревня", "Река", "Пещера"};

    @FXML
    void initialize() {
        System.out.println("Lab1Controller инициализирован!");

        // Заполняем ComboBox локациями
        startLocationComboBox.getItems().addAll(locations);
        startLocationComboBox.setValue("Замок");

        targetLocationComboBox.getItems().addAll(locations);
        targetLocationComboBox.setValue("Лес");

        // Заполняем ComboBox стратегиями
        strategyComboBox.getItems().addAll("Пешком", "На лошади", "Полёт", "Телепортация");
        strategyComboBox.setValue("Пешком");

        // Обработчики кнопок
        createHeroButton.setOnAction(event -> createHero());
        moveButton.setOnAction(event -> moveHero());
        changeStrategyButton.setOnAction(event -> changeStrategy());

        // Создаем героя по умолчанию
        createHero();
    }

    @FXML
    private void createHero() {
        String name = heroNameField.getText().trim();
        if (name.isEmpty()) {
            name = "Герой";
            heroNameField.setText(name);
        }

        String startingLocation = startLocationComboBox.getValue();
        hero = new Hero(name, startingLocation, outputArea);

        updateStatus();
        outputArea.appendText("Герой " + hero.getName() + " создан!\n");
        outputArea.appendText("Начальное местоположение: " + hero.getCurrentLocation() + "\n");
        outputArea.appendText("Способ перемещения: " + hero.getCurrentMoveStrategy() + "\n\n");
    }

    @FXML
    private void moveHero() {
        if (hero == null) {
            outputArea.appendText("Сначала создайте героя!\n\n");
            return;
        }

        String selectedLocation = targetLocationComboBox.getValue();
        if (selectedLocation.equals(hero.getCurrentLocation())) {
            outputArea.appendText("Вы уже находитесь в этой локации!\n\n");
            return;
        }

        hero.move(selectedLocation);
        updateStatus();
        outputArea.appendText("\n");
    }

    @FXML
    private void changeStrategy() {
        if (hero == null) {
            outputArea.appendText("Сначала создайте героя!\n\n");
            return;
        }

        String selectedStrategy = strategyComboBox.getValue();
        switch (selectedStrategy) {
            case "Пешком":
                hero.setMoveStrategy(new WalkStrategy(outputArea));
                break;
            case "На лошади":
                hero.setMoveStrategy(new HorseRideStrategy(outputArea));
                break;
            case "Полёт":
                hero.setMoveStrategy(new FlyStrategy(outputArea));
                break;
            case "Телепортация":
                hero.setMoveStrategy(new TeleportStrategy(outputArea));
                break;
        }
        updateStatus();
        outputArea.appendText("Способ перемещения изменен на: " + selectedStrategy + "\n\n");
    }

    @FXML
    private void updateStatus() {
        if (hero != null) {
            currentLocationLabel.setText(hero.getCurrentLocation());
            currentStrategyLabel.setText(hero.getCurrentMoveStrategy());
        }
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