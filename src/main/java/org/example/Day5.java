package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Day5 extends Day {

    Day5(boolean test) {
        super(5);
        resolvePuzzle(test);
    }

    @Override
    void testResolver() {
        System.out.println("BEGIN TESTING");

        String[] testInputArray = """
                seeds: 79 14 55 13

                seed-to-soil map:
                50 98 2
                52 50 48

                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15

                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4

                water-to-light map:
                88 18 7
                18 25 70

                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13

                temperature-to-humidity map:
                0 69 1
                1 0 69

                humidity-to-location map:
                60 56 37
                56 93 4""".split("\n");

        ArrayList<String> testInput = new ArrayList<String>(Arrays.asList(testInputArray));

        System.out.println("Testing getSeeds method...");
        long[] targetSeeds = new long[]{79, 14, 55, 13};
        long[] resultSeeds = getSeeds(testInput);
        String verdict = "FAILED";
        if (Arrays.equals(resultSeeds, targetSeeds)) {
            verdict = "PASSED";
        }
        System.out.println(verdict + "! Target seeds: " + Arrays.toString(targetSeeds) +
                " Obtained: " + Arrays.toString(resultSeeds));

        System.out.println("Testing getMap method...");
        long[][] targetMap = new long[][]{{88, 18, 7}, {18, 25, 70}};
        long[][] resultMap = getMap(testInput, "water-to-light");
        verdict = "FAILED";
        if (Arrays.deepEquals(targetMap, resultMap)) {
            verdict = "PASSED";
        }
        System.out.println(verdict + "! Target map: " + Arrays.deepToString(targetMap) +
                " Obtained: " + Arrays.deepToString(resultMap));
        targetMap = new long[][]{{60, 56, 37}, {56, 93, 4}};
        resultMap = getMap(testInput, "humidity-to-location");
        verdict = "FAILED";
        if (Arrays.deepEquals(targetMap, resultMap)) {
            verdict = "PASSED";
        }
        System.out.println(verdict + "! Target map: " + Arrays.deepToString(targetMap) +
                " Obtained: " + Arrays.deepToString(resultMap));

        System.out.println("Testing convertSourceToDestination method...");
        long[] targetDestinations = new long[]{81, 14, 57, 13};
        long[] resultDestinations = new long[4];

        long[][] seedToSoilMap = getMap(testInput, "seed-to-soil");
        for (int i = 0; i < targetSeeds.length; i++) {
            resultDestinations[i] = convertSourceToDestination(targetSeeds[i], seedToSoilMap);
        }
        verdict = "FAILED";
        if (Arrays.equals(targetDestinations, resultDestinations)) {
            verdict = "PASSED";
        }
        System.out.println(verdict + "! Target destinations: " + Arrays.toString(targetDestinations) +
                " Obtained: " + Arrays.toString(resultDestinations));

        System.out.println("TESTING CONCLUDED");
    }

    @Override
    String resolvePuzzle1() {
        long[] seeds = getSeeds(this.inputs);
        ArrayList<String> mapTypes = getMapTypes(this.inputs);

        for (String mapType : mapTypes) {
            long[][] map = getMap(this.inputs, mapType);
            for (int i = 0; i < seeds.length; i++) {
                seeds[i] = convertSourceToDestination(seeds[i], map);
            }
        }

        long lowestLocation = 9223372036854775807L;
        for (long seed : seeds) {
            if (seed < lowestLocation) {
                lowestLocation = seed;
            }
        }

        return String.valueOf(lowestLocation);
    }

    @Override
    String resolvePuzzle2() {
        return null;
    }

    public long[] getSeeds(ArrayList<String> inputs) {
        String seedLine = inputs.get(0);
        seedLine = seedLine.replace("seeds: ", "");
        String[] seedsStr = seedLine.split(" ");

        long[] seeds = new long[seedsStr.length];
        for (int i = 0; i < seeds.length; i++) {
            seeds[i] = Long.parseLong(seedsStr[i]);
        }

        return seeds;
    }

    public long[][] getMap(ArrayList<String> inputs, String mapName) {
        long[][] map = new long[0][0];

        int startIndex = -1;
        int endIndex = -1;
        boolean startIndexFound = false;
        for (int i = 0; i < inputs.size(); i++) {
            String input = inputs.get(i);
            if (input.contains(mapName) && i+1 < inputs.size()) {
                startIndex = i + 1;
                startIndexFound = true;
            }
            if (startIndexFound && input.length() == 0) {
                endIndex = i - 1;
                break;
            }
            if (startIndexFound && i == inputs.size() - 1) { // If last row
                endIndex = i;
            }
        }

        ArrayList<String> mapStrings = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            mapStrings.add(inputs.get(i));
        }

        map = new long[mapStrings.size()][3];
        for (int i = 0; i < map.length; i++) {
            String[] mapRowString = mapStrings.get(i).split(" ");
            long[] mapRow = new long[mapRowString.length];
            for (int j = 0; j < mapRowString.length; j++) {
                mapRow[j] = Long.parseLong(mapRowString[j]);
            }
            map[i] = mapRow;
        }

        return map;
    }

    public long convertSourceToDestination(long sourceNumber, long[][] sourceToDestinationMap) {
        for (long[] mapRow : sourceToDestinationMap) {
            long rangeStart = mapRow[1];
            long rangeEnd = rangeStart + mapRow[2] - 1;
            long conversionOffset = mapRow[0] - rangeStart;

            if (sourceNumber >= rangeStart && sourceNumber <= rangeEnd) {
                return sourceNumber + conversionOffset;
            }
        }

        return sourceNumber;
    }

    public ArrayList<String> getMapTypes(ArrayList<String> inputs) {
        ArrayList<String> mapTypes = new ArrayList<>();

        for (String input : inputs) {
            if (input.contains("map")) {
                mapTypes.add(input.replace(" map:", ""));
            }
        }

        return mapTypes;
    }

    public int[][] mergeMaps(int[][] sourceMap, int[][] destinationMap) {

        for (int i = 0; i < destinationMap.length; i++) {

            int destinationMapSourceRangeStart = destinationMap[i][0];
            int destinationMapSourceRangeEnd = destinationMap[i][1];

            for (int j = 0; j < sourceMap.length; j++) {

                int sourceMapDestinationRangeStart = sourceMap[j][2];
                if (destinationMapSourceRangeStart > sourceMapDestinationRangeStart) {

                    // Something goes here

                }

            }

        }

        return new int[0][0];
    }
}
