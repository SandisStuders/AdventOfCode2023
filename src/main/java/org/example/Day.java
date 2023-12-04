package org.example;

import java.util.ArrayList;

public abstract class Day {

    public String inputFilePath;
    ArrayList<String> inputs = new ArrayList<>();

    Day(int dayNumber) {
        inputFilePath = "src/main/resources/input_day" + dayNumber + ".txt";
        this.inputs = HelperFunctions.getInputs(inputFilePath);
    }

    public void resolvePuzzle(boolean test) {

        if (test) {
            testResolver();
        }

        String solution1 = resolvePuzzle1();
        System.out.println("Puzzle 1 solution: " + solution1);

        String solution2 = resolvePuzzle2();
        System.out.println("Puzzle 2 solution: " + solution2);
    }

    abstract void testResolver();

    abstract String resolvePuzzle1();

    abstract String resolvePuzzle2();

}
