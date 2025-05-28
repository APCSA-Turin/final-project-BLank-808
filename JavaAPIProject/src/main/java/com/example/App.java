package com.example;
import org.json.JSONObject;

import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.SwingUtilities;

public class App 
{
    static ArrayList<Card> allCards= new ArrayList<>();
    static int Hp=8000;
    static int Hp2=8000;
    public static void main( String[] args ) throws Exception {

     String a = API.getData("https://db.ygoprodeck.com/api/v7/cardinfo.php?type=Normal Tuner Monster");//FileLoader.returnFileAsString("CardData.txt");
     //"\"card_prices\":"   "\",\""
     String[] cardList = a.split("\"card_prices\":");
     for (String string : cardList) {
            //System.out.println(string);
            if (string.contains("name") || string.contains("Name")) {
                Card c1 = new Card(string, true);
                Thread.sleep(500);
                System.out.println(c1);
                //allCards.add(c1);
            }
            //System.out.println();
        }
    // Collections.shuffle(allCards);
    // Card[] deck= new Card[60];
    // for(int i=0; i<60; i++){
    //     deck[i] = allCards.get(i);
    // }
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             MainWindow mW = new MainWindow();
    //             for (int i = 0; i < 5; i++) {
    //                 mW.card(deck[i], "hand");
    //             }
    //             mW.imagePanel.handX=300;
    //             for (int i = 5; i < 15; i++) {
    //                 mW.card(deck[i], "handE");
    //             }
    //             mW.show();
    //         }
    //     });
    }
}
