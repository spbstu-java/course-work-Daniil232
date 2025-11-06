package com.example.coursework.lab1;

import javafx.scene.control.TextArea;

public class FlyStrategy implements MoveStrategy {
    private TextArea outputArea;

    public FlyStrategy(TextArea outputArea) {
        this.outputArea = outputArea;
    }

    @Override
    public void move(String from, String to) {
        String message = "Герой летит по воздуху из " + from + " в " + to + "\n";
        System.out.println(message);
        outputArea.appendText(message);
    }

    @Override
    public String getDescription() {
        return "Полёт";
    }
}