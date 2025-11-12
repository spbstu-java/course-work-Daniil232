package com.example.coursework.lab4.src;

import java.util.List;
import java.util.Map;

public interface StringOperations {
    List<String> convertToUpperCaseWithPrefix(List<String> strings);
    List<String> filterAndSortByFirstLetter(List<String> strings, char letter);
    Map<Character, String> getMapByFirstCharacter(List<String> strings);
}