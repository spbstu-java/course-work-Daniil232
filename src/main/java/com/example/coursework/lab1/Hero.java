package com.example.coursework.lab1;

import javafx.scene.control.TextArea;

public class Hero {
    private String name;
    private MoveStrategy moveStrategy;
    private String currentLocation;
    private TextArea outputArea;

    public Hero(String name, String startingLocation, TextArea outputArea) {
        this.name = name;
        this.moveStrategy = new WalkStrategy(outputArea);
        this.currentLocation = startingLocation;
        this.outputArea = outputArea;
    }

    public void move(String to) {
        String from = currentLocation;
        String message = name + " (" + moveStrategy.getDescription() + "): ";
        System.out.print(message);
        outputArea.appendText(message);
        moveStrategy.move(from, to);
        currentLocation = to;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
        String message = name + " теперь перемещается способом: " + moveStrategy.getDescription() + "\n";
        System.out.println(message);
        outputArea.appendText(message);
    }

    public String getCurrentMoveStrategy() {
        return moveStrategy.getDescription();
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public String getName() {
        return name;
    }
}