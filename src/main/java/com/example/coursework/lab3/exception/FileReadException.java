package com.example.coursework.lab3.exception;

public class FileReadException extends Exception {
    public FileReadException(String filePath) {
        super("Ошибка чтения файла: " + filePath);
    }

    public FileReadException(String filePath, Throwable cause) {
        super("Ошибка чтения файла: " + filePath, cause);
    }
}