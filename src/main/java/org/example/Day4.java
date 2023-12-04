package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day4 extends Day {

    Day4(boolean test) {
        super(4);
        resolvePuzzle(test);
    }

    @Override
    void testResolver() {
        String[] testInputs = new String[]{"Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                                            "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                                            "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                                            "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                                            "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                                            "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"};

        int[] testTargets = new int[]{8, 2, 2, 1, 0, 0};

        for (int i = 0; i < testInputs.length; i++) {
            String input = testInputs[i];
            int target = testTargets[i];
            int result = getPointsFromInput(input);
            String verdict;
            if (target == result) {
                verdict = "PASSED";
            }
            else {
                verdict = "FAILED";
            }
            System.out.println(verdict + "! Input: " + input + " Target: " + target + " Obtained: " + result);
        }
    }

    @Override
    String resolvePuzzle1() {
        int solution = 0;

        for (String input : inputs) {
            solution += getPointsFromInput(input);
        }

        return String.valueOf(solution);
    }

    @Override
    String resolvePuzzle2() {
        return null;
    }

    public int getPointsFromInput(String input) {
        input = input.replace("  ", " ");
        String[] cardParts = input.split(": ");
        String[] cardNumbersParts = cardParts[1].split(" \\| ");
        String[] winningNumbers = cardNumbersParts[0].split(" ");
        String[] yourNumbers = cardNumbersParts[1].split(" ");

        int matchingNumbers = countMatchingNumbers(winningNumbers, yourNumbers);

        return calculatePoints(matchingNumbers);
    }

    private int countMatchingNumbers(String[] winningNumbers, String[] yourNumbers) {
        Set<String> yourSet = new HashSet<>(Arrays.asList(yourNumbers));
        int matchingNumbers = 0;

        for (String winningNumber : winningNumbers) {
            if (yourSet.contains(winningNumber)) {
                matchingNumbers++;
            }
        }

        return matchingNumbers;
    }

    private int calculatePoints(int numberCount) {
        if (numberCount == 0) {
            return 0;
        }

        return (int) Math.pow(2, numberCount-1);
    }
}
