package com.example.coursework.lab1;

import javafx.scene.control.TextArea;

public class HorseRideStrategy implements MoveStrategy {
    private TextArea outputArea;

    public HorseRideStrategy(TextArea outputArea) {
        this.outputArea = outputArea;
    }

    @Override
    public void move(String from, String to) {
        String message = "Герой скачет на лошади из " + from + " в " + to + "\n";
        System.out.println(message);
        outputArea.appendText(message);
    }

    @Override
    public String getDescription() {
        return "На лошади";
    }
}