package com.example;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class App 
{
    static ArrayList<Card> allCards= new ArrayList<>();
    public static void main( String[] args ) throws Exception {
        String a = FileLoader.returnFileAsString("CardData.txt");
        String[] cardList = a.split("\",\"");
        for (String string : cardList) {
            //System.out.println(string);
            if (string.contains("name") || string.contains("Name")) {
                Card c1 = new Card(string);
                allCards.add(c1);
            }
        }
    Card[] deck= new Card[60];
    for(int i=0; i<10; i++){
        deck[i] = allCards.get(i);
    }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mW = new MainWindow();
                int x = 200;
                for (Card c : deck)
                    if (c != null) {
                        System.out.println(x);
                        mW.card(c, "hand", x, 100);
                        x += 100;
                    }
                mW.show();
            }
        });
    }
}
