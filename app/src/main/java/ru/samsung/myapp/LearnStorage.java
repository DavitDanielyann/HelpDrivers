package ru.samsung.myapp;

import java.util.ArrayList;
import java.util.List;

public class LearnStorage {
    private static final List<Question> incorrectAnswers = new ArrayList<>();

    public static void addIncorrectAnswer(Question question) {
        incorrectAnswers.add(question);
    }

    public static List<Question> getIncorrectAnswers() {
        return new ArrayList<>(incorrectAnswers);
    }

    public static void clear() {
        incorrectAnswers.clear();
    }
}
