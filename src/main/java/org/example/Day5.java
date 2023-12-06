package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Day5 extends Day {

    Day5(boolean test) {
        super(5);
        resolvePuzzle(test);
    }

    @Override
    void testResolver() {
        long[][] map = getMap(this.inputs, "seed-to-soil");
        System.out.println(Arrays.deepToString(map));
    }

    @Override
    String resolvePuzzle1() {
        return null;
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

    public int convertSourceToDestination(int sourceNumber, int[][] sourceToDestinationMap) {
        for (int[] mapRow : sourceToDestinationMap) {
            int rangeStart = mapRow[0];
            int rangeEnd = rangeStart + mapRow[2] - 1;
            int conversionOffset = mapRow[1] - rangeStart;

            if (sourceNumber >= rangeStart && sourceNumber <= rangeEnd) {
                return sourceNumber + conversionOffset;
            }
        }

        return sourceNumber;
    }
}
