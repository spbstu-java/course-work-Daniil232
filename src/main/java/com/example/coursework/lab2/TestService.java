package com.example.coursework.lab2;

import javafx.scene.control.TextArea;

public class TestService {

    private TextArea outputArea;

    public TestService() {
        // Конструктор по умолчанию
    }

    public TestService(TextArea outputArea) {
        this.outputArea = outputArea;
    }

    @RepeatableExecute(count = 2)
    public void publicMethod() {
        String message = "Public method executed";
        System.out.println(message);
        if (outputArea != null) outputArea.appendText(message + "\n");
    }

    @RepeatableExecute(count = 2)
    public void publicMethodWithParams(String userName, int userAge) {
        String message = "Public method - Username: " + userName + ", Age: " + userAge;
        System.out.println(message);
        if (outputArea != null) outputArea.appendText(message + "\n");
    }

    @RepeatableExecute(count = 3)
    private void privateMethod() {
        String message = "Private method executed";
        System.out.println(message);
        if (outputArea != null) outputArea.appendText(message + "\n");
    }

    @RepeatableExecute(count = 3)
    private void privateMethodWithParams(String data, int count) {
        String message = "Private method - Data: " + data + ", Count: " + count;
        System.out.println(message);
        if (outputArea != null) outputArea.appendText(message + "\n");
    }

    @RepeatableExecute(count = 4)
    protected void protectedMethod() {
        String message = "Protected method executed";
        System.out.println(message);
        if (outputArea != null) outputArea.appendText(message + "\n");
    }

    @RepeatableExecute(count = 4)
    protected void protectedMethodWithParams(String input, double value) {
        String message = "Protected method - Input: " + input + ", Value: " + value;
        System.out.println(message);
        if (outputArea != null) outputArea.appendText(message + "\n");
    }
}