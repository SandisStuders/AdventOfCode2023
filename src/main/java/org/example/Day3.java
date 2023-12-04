package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Day3 extends Day {

    char[][] charMap;

    Day3(boolean test) {
        super(3);
        transformInputsToCharMap();
        resolvePuzzle(test);
    }

    @Override
    void testResolver() {
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
            System.out.println("Digit obtained! Row: " + nextDigit.row + " First col: " + nextDigit.firstCol +
                    " Last col: " + nextDigit.lastCol);

            // Find if digit surrounded by a symbol
            boolean digitAdjacentToSymbol = digitIsAdjacentToSymbol(this.charMap, nextDigit);

            // If digit surrounded find its value and add to sum
            if (digitAdjacentToSymbol) {
                solution += getDigitValue(this.charMap, nextDigit);
                System.out.println("Digit value: " + getDigitValue(this.charMap, nextDigit));
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

    private ArrayList<Integer> getNumbersAdjacentToStar(char[][] charMap, int row, int col) {
        ArrayList<Integer> adjacentNumbers = new ArrayList<>();

        // Find all adjacent coordinates
        int prevCol = col - 1;
        int nextCol = col + 1;
        int prevRow = row - 1;
        int nextRow = row + 1;

        ArrayList<int[]> adjacentCoordinates = new ArrayList<>();
        adjacentCoordinates.add(new int[]{row, prevCol});
        adjacentCoordinates.add(new int[]{row, nextCol});
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

        for (int i = 0; i < adjacentCoordinates.size(); i++) {
            int[] coordinates = adjacentCoordinates.get(i);
            int adjacentCharRow = coordinates[0];
            int adjacentCharCol = coordinates[1];
            char c = charMap[adjacentCharRow][adjacentCharCol];
            if (Character.isDigit(c)) {
                char prevC = charMap[adjacentCharRow][adjacentCharCol - 1]; //TODO: Check if valid

                char nextC = charMap[adjacentCharRow][adjacentCharCol + 1]; //TODO: Check if valid
            }
        }

        return adjacentNumbers;
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
