package org.example;

import java.util.*;

public class Day3 extends Day {

    char[][] charMap;

    Day3(boolean test) {
        super(3);
        transformInputsToCharMap();
        resolvePuzzle(test);
    }

    @Override
    void testResolver() {
        System.out.println("TESTING STARTED");

        char[][] testCharMap = new char[][]{"467..114..".toCharArray(),
                                            "...*......".toCharArray(),
                                            "..35..633.".toCharArray(),
                                            "......#...".toCharArray(),
                                            "617*......".toCharArray(),
                                            ".....+.58.".toCharArray(),
                                            "..592.....".toCharArray(),
                                            "......755.".toCharArray(),
                                            "...$.*....".toCharArray(),
                                            ".664.598..".toCharArray()};

        int[] testIntegers = new int[]          {467,   114,    35,     633,    617,    58,     592,    755,    664,    598};
        boolean[] testAdjacency = new boolean[] {true,  false,  true,   true,   true,   false,  true,   true,   true,   true};

        int currentRow = 0;
        int currentCol = 0;
        Digit nextDigit = new Digit(0, 0, 0);

        int i = 0;
        while (nextDigit.row != -1) {
            nextDigit = findNextDigit(testCharMap, currentRow, currentCol);
            currentRow = nextDigit.row;
            currentCol = nextDigit.lastCol + 1;

            if (coordinatesAreValid(testCharMap, nextDigit.row, nextDigit.firstCol) &&
                    coordinatesAreValid(testCharMap, nextDigit.row, nextDigit.lastCol)) {

                int digitValue = getDigitValue(testCharMap, nextDigit);
                boolean digitAdjacency = digitIsAdjacentToSymbol(testCharMap, nextDigit);
                String result = "FAILED";
                if (digitValue == testIntegers[i] && digitAdjacency == testAdjacency[i]) {
                    result = "PASSED";
                }
                System.out.println(result + "! DIGIT VALUE: " + digitValue + " ADJACENCY: " + digitAdjacency +
                        " on coordinates: " + nextDigit.row + ":" + nextDigit.firstCol + "-" + nextDigit.lastCol);
                i++;
            }
        }

        System.out.println("PART 2 TESTS");
        int[][] starLocations = new int[][]{{1, 3}, {4, 3}, {8, 5}, {-1, -1}};
        ArrayList<Object[]> adjacentNumberTargets = new ArrayList<>(List.of(new Object[]{467, 35},
                new Object[]{617}, new Object[]{755, 598}, new Object[]{}));

        currentRow = 0;
        currentCol = 0;
        int[] nextStar = new int[]{0, 0};

        i = 0;
        while(nextStar[0] != -1) {
            nextStar = findNextStar(testCharMap, currentRow, currentCol);
            currentRow = nextStar[0];
            currentCol = nextStar[1] + 1;

            String verdict = "FAILED";
            if (Arrays.equals(nextStar, starLocations[i])) {
                verdict = "PASSED";
            }
            System.out.println(verdict + "! Star number " + i +
                    ". Target location: " + Arrays.toString(starLocations[i]) +
                    " Obtained location: " + Arrays.toString(nextStar));

            ArrayList<Integer> adjacentNumbers = getNumbersAdjacentToStar(testCharMap, nextStar[0], nextStar[1]);

            verdict = "FAILED";
            if (Arrays.equals(adjacentNumbers.toArray(), adjacentNumberTargets.get(i))) {
                verdict = "PASSED";
            }
            System.out.println(verdict +
                    "! Target adjacent numbers: " + Arrays.toString(adjacentNumberTargets.get(i)) +
                    " Obtained adjacent numbers: " + adjacentNumbers);

            i++;
        }

        System.out.println("TESTING CONCLUDED");
    }

    @Override
    String resolvePuzzle1() {
        int solution = 0;
        int currentRow = 0;
        int currentCol = 0;
        Digit nextDigit = new Digit(0, 0, 0);

        while (nextDigit.row != -1) {
            // Find next digit
            nextDigit = findNextDigit(this.charMap, currentRow, currentCol);
            currentRow = nextDigit.row;
            currentCol = nextDigit.lastCol + 1;

            // Find if digit surrounded by a symbol
            boolean digitAdjacentToSymbol = digitIsAdjacentToSymbol(this.charMap, nextDigit);

            // If digit surrounded find its value and add to sum
            if (digitAdjacentToSymbol) {
                solution += getDigitValue(this.charMap, nextDigit);
            }
        }

        return String.valueOf(solution);
    }

    @Override
    String resolvePuzzle2() {
        int solution = 0;
        int currentRow = 0;
        int currentCol = 0;
        int[] nextStar = new int[]{0, 0};

        while(nextStar[0] != -1) {
            nextStar = findNextStar(this.charMap, currentRow, currentCol);
            currentRow = nextStar[0];
            currentCol = nextStar[1] + 1;

            ArrayList<Integer> adjacentNumbers = getNumbersAdjacentToStar(this.charMap, nextStar[0], nextStar[1]);

            if (adjacentNumbers.size() == 2) {
                solution += adjacentNumbers.get(0) * adjacentNumbers.get(1);
            }
        }

        return String.valueOf(solution);
    }

    private int[] findNextStar(char[][] charMap, int currentRow, int currentCol) {

        for (int i = currentRow; i < charMap.length; i++) {
            for (int j = currentCol; j < charMap[0].length; j++) {
                char c = charMap[i][j];
                if (c == '*') {
                    return new int[]{i, j};
                }
            }
            currentCol = 0;
        }

        return new int[]{-1, -1};
    }

    private Digit findNextDigit(char[][] charMap, int currentRow, int currentCol) {
        boolean firstDigitObtained = false;
        int row = -1;
        int firstCol = -1;
        int lastCol = -1;

        for (int i = currentRow; i < charMap.length; i++) {
            for (int j = currentCol; j < charMap[0].length; j++) {
                char c = charMap[i][j];
                if (Character.isDigit(c)) {
                    if (!firstDigitObtained) {
                        row = i;
                        firstCol = j;
                        firstDigitObtained = true;
                    }
                }
                else {
                    if (firstDigitObtained) {
                        lastCol = j-1;
                        return new Digit(row, firstCol, lastCol);
                    }
                }
            }
            currentCol = 0;
            if (firstDigitObtained) {
                lastCol = charMap[0].length - 1;
                break;
            }
        }

        return new Digit(row, firstCol, lastCol);
    }

    private boolean digitIsAdjacentToSymbol(char[][] charMap, Digit digit) {
        // Check if digit valid
        if (!coordinatesAreValid(charMap, digit.row, digit.firstCol) ||
                !coordinatesAreValid(charMap, digit.row, digit.lastCol)) {
            return false;
        }

        // Find all adjacent coordinates
        int prevCol = digit.firstCol - 1;
        int nextCol = digit.lastCol + 1;
        int prevRow = digit.row - 1;
        int nextRow = digit.row + 1;

        ArrayList<int[]> adjacentCoordinates = new ArrayList<>();
        adjacentCoordinates.add(new int[]{digit.row, prevCol});
        adjacentCoordinates.add(new int[]{digit.row, nextCol});
        for (int i = prevCol; i <= nextCol; i++) {
            adjacentCoordinates.add(new int[]{prevRow, i});
            adjacentCoordinates.add(new int[]{nextRow, i});
        }

        // Remove coordinates that are not valid
        for (int i = adjacentCoordinates.size() - 1; i >= 0; i--) {
            int[] coordinates = adjacentCoordinates.get(i);
            if (!coordinatesAreValid(charMap, coordinates[0], coordinates[1])) {
                adjacentCoordinates.remove(i);
            }
        }

        // Check if any of coordinates contain a symbol
        for (int[] coordinates : adjacentCoordinates) {
            char c = charMap[coordinates[0]][coordinates[1]];
            if (!Character.isDigit(c) && c != '.') {
                return true;
            }
        }

        return false;
    }

    private ArrayList<Integer> getNumbersAdjacentToStar(char[][] charMap, int row, int col) {
        if (row < 0 || row >= charMap.length || col < 0 || col >= charMap[0].length) {
            return new ArrayList<>();
        }

        ArrayList<Integer> adjacentNumbers = new ArrayList<>();

        int prevCol = col - 1;
        int nextCol = col + 1;
        int prevRow = row - 1;
        int nextRow = row + 1;

        if (coordinatesAreValid(charMap, row, prevCol) && Character.isDigit(charMap[row][prevCol])) {
            int adjacentNumber = getDigitValueFromSingleCharMapLoc(charMap, row, prevCol, "left");
            adjacentNumbers.add(adjacentNumber);
        }

        if (coordinatesAreValid(charMap, row, nextCol) && Character.isDigit(charMap[row][nextCol])) {
            int adjacentNumber = getDigitValueFromSingleCharMapLoc(charMap, row, nextCol, "right");
            adjacentNumbers.add(adjacentNumber);
        }

        if (coordinatesAreValid(charMap, prevRow, col)) {
            if (Character.isDigit(charMap[prevRow][col])) {
                int adjacentNumber = getDigitValueFromSingleCharMapLoc(charMap, prevRow, col, "both");
                adjacentNumbers.add(adjacentNumber);
            }
            else {
                if (coordinatesAreValid(charMap, prevRow, prevCol) && Character.isDigit(charMap[prevRow][prevCol])) {
                    int adjacentNumber = getDigitValueFromSingleCharMapLoc(charMap, prevRow, prevCol, "left");
                    adjacentNumbers.add(adjacentNumber);
                }

                if (coordinatesAreValid(charMap, prevRow, nextCol) && Character.isDigit(charMap[prevRow][nextCol])) {
                    int adjacentNumber = getDigitValueFromSingleCharMapLoc(charMap, prevRow, nextCol, "right");
                    adjacentNumbers.add(adjacentNumber);
                }
            }
        }

        if (coordinatesAreValid(charMap, nextRow, col)) {
            if (Character.isDigit(charMap[nextRow][col])) {
                int adjacentNumber = getDigitValueFromSingleCharMapLoc(charMap, nextRow, col, "both");
                adjacentNumbers.add(adjacentNumber);
            }
            else {
                if (coordinatesAreValid(charMap, nextRow, prevCol) && Character.isDigit(charMap[nextRow][prevCol])) {
                    int adjacentNumber = getDigitValueFromSingleCharMapLoc(charMap, nextRow, prevCol, "left");
                    adjacentNumbers.add(adjacentNumber);
                }

                if (coordinatesAreValid(charMap, nextRow, nextCol) && Character.isDigit(charMap[nextRow][nextCol])) {
                    int adjacentNumber = getDigitValueFromSingleCharMapLoc(charMap, nextRow, nextCol, "right");
                    adjacentNumbers.add(adjacentNumber);
                }
            }
        }

        return adjacentNumbers;
    }

    public int getDigitValueFromSingleCharMapLoc(char[][] charMap, int row, int col, String direction) {

        int firstCol = -1;
        int lastCol = -1;
        if (Objects.equals(direction, "left")) {
            lastCol = col;
        } else if (Objects.equals(direction, "right")) {
            firstCol = col;
        }

        if (Objects.equals(direction, "left") || Objects.equals(direction, "both")) {
            for (int i = col - 1; i >= 0; i--) {
                if (!Character.isDigit(charMap[row][i])) {
                    firstCol = i + 1;
                    break;
                }
            }
            if (firstCol == -1) {
                firstCol = 0;
            }
        }

        if (Objects.equals(direction, "right") || Objects.equals(direction, "both")) {
            for (int i = col + 1; i < charMap[0].length; i++) {
                if (!Character.isDigit(charMap[row][i])) {
                    lastCol = i - 1;
                    break;
                }
            }
            if (lastCol == -1) {
                lastCol = charMap[0].length - 1;
            }
        }

        Digit digit = new Digit(row, firstCol, lastCol);
        return getDigitValue(charMap, digit);
    }

    private int getDigitValue(char[][] charMap, Digit digit) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = digit.firstCol; i <= digit.lastCol; i++) {
            stringBuilder.append(charMap[digit.row][i]);
        }

        return Integer.parseInt(stringBuilder.toString());
    }

    public boolean coordinatesAreValid(char[][] charMap, int row, int col) {
        return row >= 0 && row < charMap.length && col >= 0 && col < charMap[0].length;
    }

    public void transformInputsToCharMap() {
        int charMapRows = inputs.size();
        int charMapCols = inputs.get(0).length();
        char[][] charMap = new char[charMapRows][charMapCols];
        int i = 0;
        for (String input : inputs) {
            char[] inputChars = input.toCharArray();
            charMap[i] = inputChars;
            i++;
        }
        this.charMap = charMap;
    }

    public static class Digit {
        public int row;
        public int firstCol;
        public int lastCol;

        public Digit(int row, int firstCol, int lastCol) {
            this.row = row;
            this.firstCol = firstCol;
            this.lastCol = lastCol;
        }
    }
}
