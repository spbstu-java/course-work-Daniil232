package com.example.coursework.controller;

import com.example.coursework.lab4.ServiceFactory;
import com.example.coursework.lab4.src.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Lab4Controller {

    @FXML
    private ComboBox<String> operationComboBox;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextArea outputArea;

    @FXML
    private Button executeButton;

    @FXML
    private Button clearButton;

    private MathOperations mathService;
    private StringOperations stringService;
    private CollectionOperations collectionService;

    @FXML
    void initialize() {
        System.out.println("Lab4Controller инициализирован!");

        // Инициализация сервисов
        mathService = ServiceFactory.createMathOperations();
        stringService = ServiceFactory.createStringOperations();
        collectionService = ServiceFactory.createCollectionOperations();

        // Настройка ComboBox
        operationComboBox.getItems().addAll(
                "1. Среднее значение списка целых чисел",
                "2. Строки в верхний регистр с префиксом '_new_'",
                "3. Квадраты уникальных элементов списка",
                "4. Последний элемент коллекции",
                "5. Сумма чётных чисел массива",
                "6. Преобразование строк в Map (первый символ - ключ)"
        );
        operationComboBox.setValue("1. Среднее значение списка целых чисел");

        // Обработчики кнопок
        executeButton.setOnAction(event -> executeOperation());
        clearButton.setOnAction(event -> clearAll());

        outputArea.appendText("Лабораторная работа 4: Stream API и коллекции\n");
        outputArea.appendText("Выберите операцию и введите данные\n\n");

        updateInputPrompt();
    }

    @FXML
    private void executeOperation() {
        String selectedOperation = operationComboBox.getValue();
        if (selectedOperation == null) {
            outputArea.appendText("Выберите операцию!\n");
            return;
        }

        String inputText = inputTextArea.getText().trim();
        if (inputText.isEmpty()) {
            outputArea.appendText("Введите данные!\n");
            return;
        }

        try {
            int operationNumber = Integer.parseInt(selectedOperation.substring(0, 1));

            switch (operationNumber) {
                case 1 -> executeAverage(inputText);
                case 2 -> executeUpperCaseWithPrefix(inputText);
                case 3 -> executeUniqueSquares(inputText);
                case 4 -> executeLastElement(inputText);
                case 5 -> executeSumEven(inputText);
                case 6 -> executeMapByFirstCharacter(inputText);
                default -> outputArea.appendText("Неизвестная операция\n");
            }

        } catch (Exception e) {
            outputArea.appendText("Ошибка: " + e.getMessage() + "\n");
        }
    }

    private void executeAverage(String inputText) {
        try {
            List<Integer> numbers = parseIntegerList(inputText);
            if (numbers.isEmpty()) {
                outputArea.appendText("Список пуст. Невозможно вычислить среднее значение.\n");
                return;
            }

            double average = mathService.getAverage(numbers);
            outputArea.appendText("Входные данные: " + numbers + "\n");
            outputArea.appendText("Среднее значение: " + String.format("%.2f", average) + "\n");
            outputArea.appendText("---\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Ошибка: введите числа через пробел! Например: 1 2 3 4\n");
        }
    }

    private void executeUpperCaseWithPrefix(String inputText) {
        List<String> strings = parseStringList(inputText);
        List<String> result = stringService.convertToUpperCaseWithPrefix(strings);

        outputArea.appendText("Входные данные: " + strings + "\n");
        outputArea.appendText("Результат: " + result + "\n");
        outputArea.appendText("---\n");
    }

    private void executeUniqueSquares(String inputText) {
        try {
            List<Integer> numbers = parseIntegerList(inputText);
            List<Integer> uniqueSquares = mathService.getUniqueElementsSquared(numbers);

            outputArea.appendText("Входные данные: " + numbers + "\n");
            outputArea.appendText("Квадраты уникальных элементов: " + uniqueSquares + "\n");
            outputArea.appendText("---\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Ошибка: введите числа через пробел! Например: 1 2 3 4\n");
        }
    }

    private void executeLastElement(String inputText) {
        List<String> collection = parseStringList(inputText);

        try {
            String lastElement = collectionService.getLastElement(collection);
            outputArea.appendText("Входные данные: " + collection + "\n");
            outputArea.appendText("Последний элемент: " + lastElement + "\n");
            outputArea.appendText("---\n");

        } catch (EmptyCollectionException e) {
            outputArea.appendText("Ошибка: " + e.getMessage() + "\n");
        }
    }

    private void executeSumEven(String inputText) {
        try {
            List<Integer> numbers = parseIntegerList(inputText);
            int[] array = numbers.stream().mapToInt(Integer::intValue).toArray();
            int sum = mathService.sumOfEvenNumbers(array);

            outputArea.appendText("Входные данные: " + numbers + "\n");
            outputArea.appendText("Сумма четных чисел: " + sum + "\n");
            outputArea.appendText("---\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Ошибка: введите числа через пробел! Например: 1 2 3 4\n");
        }
    }

    private void executeMapByFirstCharacter(String inputText) {
        List<String> strings = parseStringList(inputText);

        // Используем исправленный метод для Map
        Map<Character, List<String>> result = getMapByFirstCharacterFixed(strings);

        outputArea.appendText("Входные данные: " + strings + "\n");
        outputArea.appendText("Результат преобразования:\n");

        // Красиво форматируем вывод Map
        for (Map.Entry<Character, List<String>> entry : result.entrySet()) {
            outputArea.appendText("  '" + entry.getKey() + "' -> " + entry.getValue() + "\n");
        }
        outputArea.appendText("---\n");
    }

    // Исправленный метод для 6 задания - группируем по первому символу
    private Map<Character, List<String>> getMapByFirstCharacterFixed(List<String> strings) {
        return strings.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.groupingBy(
                        s -> s.charAt(0),
                        Collectors.mapping(
                                s -> s.length() > 1 ? s.substring(1) : "[пустая строка]",
                                Collectors.toList()
                        )
                ));
    }

    private List<Integer> parseIntegerList(String input) {
        return Arrays.stream(input.split("\\s+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private List<String> parseStringList(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    @FXML
    private void clearAll() {
        inputTextArea.clear();
        outputArea.clear();
        outputArea.appendText("Очищено. Выберите операцию и введите данные.\n");
        updateInputPrompt();
    }

    @FXML
    private void onOperationChanged() {
        updateInputPrompt();
    }

    private void updateInputPrompt() {
        String selected = operationComboBox.getValue();
        if (selected == null) return;

        int operationNumber = Integer.parseInt(selected.substring(0, 1));

        switch (operationNumber) {
            case 1, 3, 5 -> inputTextArea.setPromptText("Введите числа через пробел (например: 1 2 3 4)");
            case 2, 4, 6 -> inputTextArea.setPromptText("Введите строки через запятую (например: apple, banana, cherry)");
            default -> inputTextArea.setPromptText("Введите данные...");
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