package com.example.coursework.lab3.src;

import com.example.coursework.lab3.exception.FileReadException;
import com.example.coursework.lab3.exception.InvalidFileFormatException;

public class TextProcessorBuilder {
    public static TextConverter buildBilingualProcessor(String dictionaryPath)
            throws FileReadException, InvalidFileFormatException {
        EnglishRussianTranslator processor = new EnglishRussianTranslator();
        processor.initializeWordBase(dictionaryPath);
        return processor;
    }
}