package com.example;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.SwingUtilities;

public class App 
{
    static ArrayList<Card> allCards= new ArrayList<>();
    static int Hp=8000;
    static int Hp2=8000;
    public static void main( String[] args ) throws Exception {
    String a = FileLoader.returnFileAsString("CardData.txt");
    // String[] list= a.split(";");
    // for(String str: list){
    //     System.out.println(new Card(API.getData("https://db.ygoprodeck.com/api/v7/cardinfo.php?name="+str+""), true));
    //     Thread.sleep(500);
    // }
     //"\"card_prices\":"   "\",\""
     String[] cardList = a.split("\",\"");
     for (String string : cardList) {
        Card c1;
            if (string.contains("name") || string.contains("Name")) {
                if(string.contains("Type: Spell Card") || string.contains("Type: Trap Card") ){
                c1 = new SpellTrap(string);
                }else{
                c1= new Monster(string);
                }
                // Thread.sleep(500);
                // System.out.println(c1);
                allCards.add(c1);
            }
        }
    Collections.shuffle(allCards);
    Card[] deck= new Card[60];
    for(int i=0; i<60; i++){
        deck[i] = allCards.get(i);
    }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mW = new MainWindow();
                for (int i = 0; i < 5; i++) {
                    mW.card(deck[i], "hand");
                }
                mW.imagePanel.handX=300;
                for (int i = 5; i < 15; i++) {
                    mW.card(deck[i], "handE");
                }
                mW.start();
            }
        });
    }
}
