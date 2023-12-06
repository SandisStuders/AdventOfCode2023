package org.example;

public class Day6 extends Day {

    Day6(boolean test) {
        super(6);
        resolvePuzzle(test);
    }

    @Override
    void testResolver() {
        System.out.println("TESTING STARTED");

        String timeInput = "Time:      7  15   30";
        String distanceInput = "Distance:  9  40  200";
        int[] individualTargets = new int[]{4, 8, 9};

        int[] times = parseInput(timeInput, "Time:");
        int[] distances = parseInput(distanceInput, "Distance:");

        for (int i = 0; i < times.length; i++) {
            int time = times[i];
            int distance = distances[i];
            int target = individualTargets[i];
            String verdict = "FAILED";
            int result = getRecordBeatingRaces(time, distance);
            if(result == target) {
                verdict = "PASSED";
            }
            System.out.println(verdict + "! TIME: " + time + " DISTANCE: " + distance + " RESULT: " + result);
        }

        System.out.println("TESTING CONCLUDED");
    }

    @Override
    String resolvePuzzle1() {
        int[] times = parseInput(inputs.get(0), "Time:");
        int[] distances = parseInput(inputs.get(1), "Distance:");

        int solution = 1;
        for (int i = 0; i < times.length; i++) {
            solution *= getRecordBeatingRaces(times[i], distances[i]);
        }

        return String.valueOf(solution);
    }

    @Override
    String resolvePuzzle2() {
        long time = parseInputWithKerning(inputs.get(0), "Time:");
        long distance = parseInputWithKerning(inputs.get(1), "Distance:");

        return String.valueOf(getRecordBeatingRaces(time, distance));
    }

    public int[] parseInput(String input, String removableText) {
        input = input.replace(removableText, "");
        input = input.trim().replaceAll(" +", " ");
        String[] inputs = input.split(" ");
        int[] intInputs = new int[inputs.length];
        for (int i = 0; i < intInputs.length; i++) {
            intInputs[i] = Integer.parseInt(inputs[i]);
        }
        return intInputs;
    }

    public long parseInputWithKerning(String input, String removableText) {
        input = input.replace(removableText, "");
        return Long.parseLong(input.trim().replaceAll(" +", ""));
    }

    public int getRecordBeatingRaces(long time, long distance) {
        long a = -1;
        double[] roots = getQuadraticEquationRoots(a, time, -distance);
        return getRecordBeatingRacesFromRoots(roots);
    }

    public int getRecordBeatingRacesFromRoots(double[] roots) {
        double smallerRoot = roots[0];
        double biggerRoot = roots[1];
        if (roots[0] > roots[1]) {
            smallerRoot = roots[1];
            biggerRoot = roots[0];
        }

        double epsilon = 0.0000000001;
        biggerRoot -= epsilon;
        smallerRoot += epsilon;

        return  (int) (Math.floor(biggerRoot) - Math.ceil(smallerRoot) + 1);
    }

    double[] getQuadraticEquationRoots(long a, long b, long c) {
        double[] roots = new double[2];

        double discriminant = Math.pow(b, 2) - 4 * a * c;
        roots[0] = (-b - Math.sqrt(discriminant)) / 2 * a;
        roots[1] = (-b + Math.sqrt(discriminant)) / 2 * a;

        return roots;
    }

}
