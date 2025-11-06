package com.example.coursework.lab3.src;

import com.example.coursework.lab3.exception.FileReadException;
import com.example.coursework.lab3.exception.InvalidFileFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EnglishRussianTranslator implements TextConverter {
    private final Map<String, String> wordMap = new HashMap<>();
    private int longestSequence;
    private boolean baseInitialized = false;

    @Override
    public void initializeWordBase(String filePath) throws FileReadException, InvalidFileFormatException {
        wordMap.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            int counter = 0;

            while ((currentLine = reader.readLine()) != null) {
                counter++;
                currentLine = currentLine.trim();
                if (currentLine.isEmpty()) continue;

                String[] segments = currentLine.split("\\|", 2);
                if (segments.length != 2) {
                    throw new InvalidFileFormatException("Некорректный формат в строке " + counter);
                }

                String sourceWord = segments[0].trim().toLowerCase();
                String targetWord = segments[1].trim();

                if (sourceWord.isEmpty()) {
                    throw new InvalidFileFormatException("Отсутствует исходное слово в строке " + counter);
                }

                this.wordMap.put(sourceWord, targetWord);
            }

            if (wordMap.isEmpty()) {
                throw new InvalidFileFormatException("База слов пуста");
            }

            this.longestSequence = findLongestWordSequence();
            this.baseInitialized = true;

        } catch (IOException e) {
            throw new FileReadException(filePath, e);
        }
    }

    @Override
    public String convertText(String text) {
        if (!baseInitialized) {
            throw new IllegalStateException("База слов не инициализирована");
        }

        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        String[] textParts = text.toLowerCase().split("(?<=[.!?])\\s*");
        StringBuilder resultBuilder = new StringBuilder();

        for (String part : textParts) {
            part = part.trim();
            if (!part.isEmpty()) {
                String convertedPart = processTextPart(part);
                if (!convertedPart.isEmpty()) {
                    resultBuilder.append(convertedPart).append(" ");
                }
            }
        }

        return resultBuilder.toString().trim();
    }

    @Override
    public boolean hasEntry(String word) {
        return wordMap.containsKey(word.toLowerCase());
    }

    private int findLongestWordSequence() {
        int maxWords = 0;
        for (String key : wordMap.keySet()) {
            int wordCount = key.split(" ").length;
            if (wordCount > maxWords) {
                maxWords = wordCount;
            }
        }
        return maxWords;
    }

    private String processTextPart(String textPart) {
        String cleanedText = textPart.replaceAll("[^a-zA-Zа-яА-Я0-9\\s]", " ");
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(cleanedText.split("\\s+")));
        wordList.removeIf(String::isEmpty);

        int position = 0;
        while (position < wordList.size()) {
            boolean foundMatch = false;

            for (int seqLength = Math.min(longestSequence, wordList.size() - position); seqLength > 0; seqLength--) {
                String wordGroup = String.join(" ", wordList.subList(position, position + seqLength));

                if (wordMap.containsKey(wordGroup)) {
                    String convertedWord = wordMap.get(wordGroup);
                    if (!convertedWord.isEmpty()) {
                        wordList.set(position, convertedWord);
                    } else {
                        wordList.set(position, "");
                    }

                    for (int j = 1; j < seqLength; j++) {
                        wordList.remove(position + 1);
                    }

                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                position++;
            }
        }

        wordList.removeIf(String::isEmpty);

        if (wordList.isEmpty()) {
            return "";
        }

        return formatResult(wordList, textPart);
    }

    private String formatResult(ArrayList<String> words, String originalText) {
        if (!words.isEmpty()) {
            String firstWord = words.get(0);
            if (!firstWord.isEmpty()) {
                words.set(0, makeFirstLetterCapital(firstWord));
            }
        }

        if (!words.isEmpty()) {
            String lastWord = words.get(words.size() - 1);
            char finalChar = originalText.charAt(originalText.length() - 1);
            if (!Character.isLetterOrDigit(finalChar) && !Character.isWhitespace(finalChar)) {
                words.set(words.size() - 1, lastWord + finalChar);
            }
        }

        return String.join(" ", words);
    }

    private String makeFirstLetterCapital(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}