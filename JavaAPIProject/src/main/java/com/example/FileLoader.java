package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;

public class FileLoader {
    public static String returnFileAsString(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            StringBuilder text= new StringBuilder();
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
            return(text.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Error";
    }
}
