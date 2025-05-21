package com.example;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class App 
{
    static ArrayList<Card> allCards= new ArrayList<>();
    public static void main( String[] args ) throws Exception
    {
        String a = FileLoader.returnFileAsString("CardData.txt");
        String[] cardList= a.split("\",\"");
        for (String string : cardList) {
            //System.out.println(string);
            if(string.contains("name")|| string.contains("Name")){
            Card c1= new Card(string);
            allCards.add(c1);
            System.out.println(c1);
            System.out.println();
            }
        }
    Card[] deck= new Card[60];
    for(int i=0;i<10;i++){
        deck[i]=allCards.get(i);
    }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                MainWindow mW= new MainWindow();
                for (Card c : deck) {
                    if(c!=null){
                        mW.card();
                    }
                }
                mW.show();
            }
        });
    }
}
