package com.example.coursework.lab4.src;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MathOperationsImpl implements MathOperations {

    @Override
    public double getAverage(List<Integer> numbers) {
        return numbers.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
    }

    @Override
    public List<Integer> getUniqueElementsSquared(List<Integer> numbers) {
        return numbers.stream()
                .filter(i -> numbers.stream().filter(i::equals).count() == 1)
                .map(i -> i * i)
                .collect(Collectors.toList());
    }

    @Override
    public int sumOfEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }
}