package com.example.coursework.lab3.src;

import com.example.coursework.lab3.exception.FileReadException;
import com.example.coursework.lab3.exception.InvalidFileFormatException;

public interface TextConverter {
    void initializeWordBase(String filePath) throws FileReadException, InvalidFileFormatException;
    String convertText(String text);
    boolean hasEntry(String word);
}