package com.example.coursework.lab4.src;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringOperationsImpl implements StringOperations {

    @Override
    public List<String> convertToUpperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> filterAndSortByFirstLetter(List<String> strings, char letter) {
        return strings.stream()
                .filter(s -> !s.isEmpty() && s.charAt(0) == letter)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Map<Character, String> getMapByFirstCharacter(List<String> strings) {
        return strings.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.length() > 1 ? s.substring(1) : "",
                        (existing, replacement) -> existing + ", " + replacement // Объединяем значения при совпадении ключей
                ));
    }
}