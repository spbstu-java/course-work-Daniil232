package com.example.coursework.controller;

import com.example.coursework.lab3.src.TextConverter;
import com.example.coursework.lab3.src.TextProcessorBuilder;
import com.example.coursework.lab3.exception.FileReadException;
import com.example.coursework.lab3.exception.InvalidFileFormatException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Lab3Controller {

    @FXML
    private TextField inputTextField;

    @FXML
    private Button translateButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextArea outputArea;

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    private TextConverter textProcessor;
    private static final String DICTIONARY_PATH = "/com/example/coursework/dictionary.txt";

    @FXML
    void initialize() {
        System.out.println("Lab3Controller инициализирован!");

        // Обработчики кнопок
        translateButton.setOnAction(event -> translateText());
        clearButton.setOnAction(event -> clearText());

        // Автоперевод при нажатии Enter
        inputTextField.setOnAction(event -> translateText());

        outputArea.appendText("Лабораторная работа 3: Переводчик\n");
        outputArea.appendText("Введите текст на английском для перевода\n\n");

        // Инициализация переводчика
        initializeTranslator();
    }

    private void initializeTranslator() {
        statusLabel.setText("Загрузка словаря...");
        progressIndicator.setVisible(true);

        new Thread(() -> {
            try {
                // Копируем словарь во временный файл
                String tempDictionaryPath = copyDictionaryToTempFile();

                javafx.application.Platform.runLater(() -> {
                    try {
                        textProcessor = TextProcessorBuilder.buildBilingualProcessor(tempDictionaryPath);
                        statusLabel.setText("Словарь загружен");
                        outputArea.appendText("✓ Словарь успешно загружен!\n");
                    } catch (Exception e) {
                        handleDictionaryError(e);
                    } finally {
                        progressIndicator.setVisible(false);
                    }
                });

            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    handleDictionaryError(e);
                    progressIndicator.setVisible(false);
                });
            }
        }).start();
    }

    private String copyDictionaryToTempFile() throws IOException {
        // Получаем ресурс как поток
        InputStream dictionaryStream = getClass().getResourceAsStream(DICTIONARY_PATH);

        if (dictionaryStream == null) {
            throw new IOException("Файл словаря не найден в ресурсах: " + DICTIONARY_PATH);
        }

        // Создаем временный файл
        Path tempFile = Files.createTempFile("dictionary", ".txt");

        // Копируем содержимое
        Files.copy(dictionaryStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        dictionaryStream.close();

        return tempFile.toAbsolutePath().toString();
    }

    private void handleDictionaryError(Exception e) {
        if (e instanceof FileReadException) {
            statusLabel.setText("Ошибка загрузки");
            outputArea.appendText("✗ Ошибка чтения файла: " + e.getMessage() + "\n");
        } else if (e instanceof InvalidFileFormatException) {
            statusLabel.setText("Ошибка формата");
            outputArea.appendText("✗ Ошибка формата словаря: " + e.getMessage() + "\n");
        } else {
            statusLabel.setText("Ошибка");
            outputArea.appendText("✗ Ошибка инициализации: " + e.getMessage() + "\n");
            e.printStackTrace();
        }

        outputArea.appendText("Проверьте наличие файла dictionary.txt в ресурсах\n");
    }

    @FXML
    private void translateText() {
        String inputText = inputTextField.getText().trim();

        if (inputText.isEmpty()) {
            outputArea.appendText("Введите текст для перевода\n");
            return;
        }

        if (textProcessor == null) {
            outputArea.appendText("Ошибка: Переводчик не инициализирован\n");
            return;
        }

        if ("exit".equalsIgnoreCase(inputText)) {
            goBackToMain();
            return;
        }

        if ("help".equalsIgnoreCase(inputText)) {
            showHelp();
            return;
        }

        try {
            statusLabel.setText("Перевод...");
            progressIndicator.setVisible(true);

            // Запускаем перевод в отдельном потоке
            new Thread(() -> {
                try {
                    String translatedText = textProcessor.convertText(inputText);

                    javafx.application.Platform.runLater(() -> {
                        outputArea.appendText("Ввод: " + inputText + "\n");
                        outputArea.appendText("Перевод: " + translatedText + "\n");
                        outputArea.appendText("---\n");
                        statusLabel.setText("Переведено");
                        progressIndicator.setVisible(false);

                        // Очищаем поле ввода после успешного перевода
                        inputTextField.clear();
                    });

                } catch (Exception e) {
                    javafx.application.Platform.runLater(() -> {
                        outputArea.appendText("Ошибка при переводе: " + e.getMessage() + "\n");
                        statusLabel.setText("Ошибка");
                        progressIndicator.setVisible(false);
                    });
                }
            }).start();

        } catch (Exception e) {
            outputArea.appendText("Ошибка при запуске перевода: " + e.getMessage() + "\n");
            statusLabel.setText("Ошибка");
            progressIndicator.setVisible(false);
        }
    }

    @FXML
    private void clearText() {
        inputTextField.clear();
        outputArea.clear();
        statusLabel.setText("Готово");
        outputArea.appendText("Очищено. Введите текст для перевода.\n");
    }

    private void showHelp() {
        outputArea.appendText("\n=== СПРАВКА ===\n");
        outputArea.appendText("• Введите текст на английском для перевода\n");
        outputArea.appendText("• Переводчик использует словарь из файла dictionary.txt\n");
        outputArea.appendText("• Выбирается самый длинный подходящий вариант перевода\n");
        outputArea.appendText("• Для выхода введите 'exit'\n");
        outputArea.appendText("• Для очистки нажмите 'Очистить'\n");
        outputArea.appendText("===============\n\n");
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