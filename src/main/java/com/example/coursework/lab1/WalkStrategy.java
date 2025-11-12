package com.example.coursework.lab1;

import javafx.scene.control.TextArea;

public class WalkStrategy implements MoveStrategy {
    private TextArea outputArea;

    public WalkStrategy(TextArea outputArea) {
        this.outputArea = outputArea;
    }

    @Override
    public void move(String from, String to) {
        String message = "Герой идет пешком из " + from + " в " + to + "\n";
        System.out.println(message);
        outputArea.appendText(message);
    }

    @Override
    public String getDescription() {
        return "Пешком";
    }
}