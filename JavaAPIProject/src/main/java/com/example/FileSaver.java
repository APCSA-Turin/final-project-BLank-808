package com.example;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

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

    public static void saveCardImage(String url, String id){
        String filename = "JavaAPIProject\\src\\main\\media\\"+ id +".jpg";
        try {
            URL imageUrl = new URL(url);
            BufferedImage image = ImageIO.read(imageUrl);
            File file=new File(filename);
            ImageIO.write(image, "jpg",file);
            System.out.println("Image downloaded successfully: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

