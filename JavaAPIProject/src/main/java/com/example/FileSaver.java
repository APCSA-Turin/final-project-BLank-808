package com.example;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class FileSaver {
        public static void saveData(String data) {
        try (BufferedWriter bw= new BufferedWriter(new FileWriter("CardData.txt", true))) {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}

