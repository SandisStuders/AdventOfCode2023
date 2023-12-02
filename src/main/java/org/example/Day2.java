package org.example;

public class Day2 extends Day {

    int maxRedCubes = 12;
    int maxGreenCubes = 13;
    int maxBlueCubes = 14;

    Day2(boolean test) {
        super(2);
        resolvePuzzle(test);
    }

    @Override
    void testResolver() {
        String[] testInputs = new String[]{"Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"};
        int[] testTargets = new int[] {1, 2, 0, 0, 5};

        for (int i = 0; i < testInputs.length; i++) {
            String input = testInputs[i];
            int target = testTargets[i];
            int result = scoreToAddFromGame(input);
            String verdict;
            if (target == result) {
                verdict = "PASSED";
            }
            else {
                verdict = "FAILED";
            }
            System.out.println(verdict + "! Input: " + input + " Target: " + target + " Obtained: " + result);
        }

        int[] testTargets2 = new int[] {48, 12, 1560, 630, 36};

        for (int i = 0; i < testInputs.length; i++) {
            String input = testInputs[i];
            int target = testTargets2[i];
            int result = getPowerOfFewestCubes(input);
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
        int idSum = 0;
        for (String input : inputs) {
            idSum += scoreToAddFromGame(input);
        }
        return String.valueOf(idSum);
    }

    @Override
    String resolvePuzzle2() {
        int powerOfFewestSets = 0;
        for (String input : inputs) {
            powerOfFewestSets += getPowerOfFewestCubes(input);
        }

        return String.valueOf(powerOfFewestSets);
    }

    public int scoreToAddFromGame(String input) {
        String[] splitString = input.split(": ");
        String idDetails = splitString[0];
        String gameDetails = splitString[1];

        int gameId = Integer.parseInt(idDetails.replace("Game ", ""));

        String[] sets = gameDetails.split("; ");
        for (String set : sets) {
            String[] cubes = set.split(", ");
            for (String cube : cubes) {
                String[] cubeDetails = cube.split(" ");
                int cubeCount = Integer.parseInt(cubeDetails[0]);
                String cubeColor = cubeDetails[1];
                if (cubeColor.equals("red") && cubeCount > maxRedCubes ||
                        cubeColor.equals("green") && cubeCount > maxGreenCubes ||
                        cubeColor.equals("blue") && cubeCount > maxBlueCubes) {
                    return 0;
                }
            }
        }

        return gameId;
    }

    private int getPowerOfFewestCubes(String input) {
        int maxRedCubesInGame = 0;
        int maxGreenCubesInGame = 0;
        int maxBlueCubesInGame = 0;

        String[] splitString = input.split(": ");
        String gameDetails = splitString[1];

        String[] sets = gameDetails.split("; ");
        for (String set : sets) {
            String[] cubes = set.split(", ");
            for (String cube : cubes) {
                String[] cubeDetails = cube.split(" ");
                int cubeCount = Integer.parseInt(cubeDetails[0]);
                String cubeColor = cubeDetails[1];
                if (cubeColor.equals("red") && cubeCount > maxRedCubesInGame) {
                    maxRedCubesInGame = cubeCount;
                } else if (cubeColor.equals("green") && cubeCount > maxGreenCubesInGame) {
                    maxGreenCubesInGame = cubeCount;
                } else if (cubeColor.equals("blue") && cubeCount > maxBlueCubesInGame) {
                    maxBlueCubesInGame = cubeCount;
                }
            }
        }

        return maxRedCubesInGame * maxGreenCubesInGame * maxBlueCubesInGame;
    }
}
