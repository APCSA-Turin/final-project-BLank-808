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
    // String a = FileLoader.returnFileAsString("CardData.txt");
    // String[] list= a.split(";");
    // for(String str: list){
    //     System.out.println(new Card(API.getData("https://db.ygoprodeck.com/api/v7/cardinfo.php?name="+str+""), true));
    //     Thread.sleep(500);
    // }
     //"\"card_prices\":"   "\",\""
    //  String[] cardList = a.split("\",\"");
    //  for (String string : cardList) {
    //     Card c1;
    //         if (string.contains("name") || string.contains("Name")) {
    //             if(string.contains("Type: Spell Card") || string.contains("Type: Trap Card") ){
    //             c1 = new SpellTrap(string);
    //             }else{
    //             c1= new Monster(string);
    //             }
    //             // Thread.sleep(500);
    //             // System.out.println(c1);
    //             allCards.add(c1);
    //         }
    //     }
    // Collections.shuffle(allCards);
    // Card[] deck= new Card[60];
    // for(int i=0; i<60; i++){
    //     deck[i] = allCards.get(i);
    // }
        Player p1= new Player(false, "Player1");
        p1.Hp=Hp;
        shuffle(p1.mDeck);
        Player p2= new Player(true, "Player2");
        p2.Hp=Hp2;
        shuffle(p2.mDeck);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mW = new MainWindow(p1,p2);
                for (int i = 0; i < 5; i++) {
                    if(p1.mDeck[i]!=null){
                    mW.card(p1.mDeck[i], "Player1");
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if(p2.mDeck[i]!=null){
                    mW.card(p2.mDeck[i], "Player2");
                    }
                }
                mW.start();
                }
        });
    }

    static void shuffle(Card[] array) {
        int currentIndex = array.length;

        // While there remain elements to shuffle...
        while (currentIndex != 0) {

            // Pick a remaining element...
            int randomIndex = (int)Math.floor(Math.random() * currentIndex);
            currentIndex--;
            if(array[currentIndex]!=null&& array[randomIndex]!=null){
            Card temp=array[currentIndex];
            // And swap it with the current element.
            array[currentIndex]= array[randomIndex];
            array[randomIndex]= temp;
            }
        }
    }
}
