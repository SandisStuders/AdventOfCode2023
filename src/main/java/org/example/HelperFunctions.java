package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HelperFunctions {

    public static ArrayList<String> getInputs(String filePath) {
        ArrayList<String> inputs = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            while (br.ready()) {
                inputs.add(br.readLine());
            }
        }
        catch (IOException e) {
            System.out.println("Couldn't read inputs!");
        }

        return inputs;
    }

}
