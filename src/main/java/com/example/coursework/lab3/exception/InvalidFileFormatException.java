package com.example.coursework.lab3.exception;

public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String message) {
        super("Неверный формат файла: " + message);
    }

    public InvalidFileFormatException(String message, Throwable cause) {
        super("Неверный формат файла: " + message, cause);
    }
}