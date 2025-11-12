package com.example.coursework.lab4;

import com.example.coursework.lab4.src.*;

public class ServiceFactory {

    public static MathOperations createMathOperations() {
        return new MathOperationsImpl();
    }

    public static StringOperations createStringOperations() {
        return new StringOperationsImpl();
    }

    public static CollectionOperations createCollectionOperations() {
        return new CollectionOperationsImpl();
    }
}