package org.example;

public class Day1 extends Day {

    Day1(boolean test) {

        super(1);
        resolvePuzzle(test);

    }

    @Override
    public void testResolver() {
        String[] testInputs = new String[]{"1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet"};
        int[] testTargets = new int[] {12, 38, 15, 77};

        for (int i = 0; i < testInputs.length; i++) {
            String input = testInputs[i];
            int target = testTargets[i];
            int result = getDigitsFromInput(input, false);
            String verdict;
            if (target == result) {
                verdict = "PASSED";
            }
            else {
                verdict = "FAILED";
            }
            System.out.println(verdict + "! Input: " + input + " Target: " + target + " Obtained: " + result);
        }

        String[] testInputs2 = new String[]
                {"two1nine", "eightwothree", "abcone2threexyz", "xtwone3four", "4nineeightseven2", "zoneight234",
                        "7pqrstsixteen"};
        int[] testTargets2 = new int[] {29, 83, 13, 24, 42, 14, 76};

        for (int i = 0; i < testInputs2.length; i++) {
            String input = testInputs2[i];
            int target = testTargets2[i];
            int result = getDigitsFromInput(input, true);
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
    public String resolvePuzzle1() {
        int solution = 0;
        for (String input : inputs) {
            solution += getDigitsFromInput(input, false);
        }

        return String.valueOf(solution);
    }

    @Override
    public String resolvePuzzle2() {
        int solution = 0;
        for (String input : inputs) {
            solution += getDigitsFromInput(input, true);
        }

        return String.valueOf(solution);
    }

    public int getDigitsFromInput(String input, boolean includeTextualNumbers) {
        String[] digitsInText = new String[]
                {"1", "2", "3", "4", "5", "6", "7", "8", "9",
                        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String[] representedDigits = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9",
                        "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        int earliestDigitPosition = input.length();
        int latestDigitPosition = -1;
        String firstDigit = null;
        String lastDigit = null;

        int arraySearchUntilIndex = digitsInText.length;
        if (!includeTextualNumbers) {
            arraySearchUntilIndex = 10;
        }

        for (int i = 0; i < arraySearchUntilIndex; i++) {
            int firstPositionInText = input.indexOf(digitsInText[i]);
            int lastPositionInText = input.lastIndexOf(digitsInText[i]);
            if (firstPositionInText != -1) {
                if (lastPositionInText > latestDigitPosition) {
                    lastDigit = representedDigits[i];
                    latestDigitPosition = lastPositionInText;
                }
                if (firstPositionInText < earliestDigitPosition) {
                    firstDigit = representedDigits[i];
                    earliestDigitPosition = firstPositionInText;
                }
            }
        }

        String concatenatedDigits = firstDigit + lastDigit;
        return Integer.parseInt(concatenatedDigits);
    }
}
