package com.example.coursework.lab4.src;

public class EmptyCollectionException extends RuntimeException {
    public EmptyCollectionException(String message) {
        super(message);
    }

    public EmptyCollectionException(String message, Throwable cause) {
        super(message, cause);
    }
}