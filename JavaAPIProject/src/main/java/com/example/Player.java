package com.example;

import java.util.ArrayList;

public class Player {
    Card[] mDeck,deck1,deck2,deck3= new Card[60];
    ArrayList<Card> hand;
    int pos=4;
    String Name;
    boolean autoPlay;
    public Player(boolean bot, String name){
        autoPlay=bot;
        Name=name;
        for(int i=1; i<=3; i++){
            String a = FileLoader.returnFileAsString("Deck"+i+".txt");
            String[] cardList = a.split("\",\"");
            for(int j=0; j<60; j++){
                String string= cardList[j];
                if (string.contains("name") || string.contains("Name")) {
                    Card c1 = new Card(string);
                    if(i==1){
                    deck1[j]=c1;
                    }else if(i==2){
                    deck2[j]=c1;
                    }else{
                    deck3[j]=c1;
                    }
                }else{
                    break;
                }
            }
        }
        mDeck=deck1;
    }

    public boolean canPlay(){
        return(mDeck[0]!=null);
    }

    public void Play(MainWindow mw){
        // if(autoPlay){
        //     if(mw.phase=4){
        //         mw.card(mDeck[pos], Name);
        //         evaluateHand();
        //     }
        // }
    }

    public void setHand(ArrayList<Card> h){
        hand=h;
    }

    public ArrayList<Card> evaluateHand(){
        return hand;
    }
}
