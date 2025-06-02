package com.example;

import java.util.ArrayList;

import com.example.ImagePanel.DraggableImage;
import com.example.ImagePanel.Zone;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Player {
    Card[] mDeck,deck1,deck2,deck3= new Card[60];
    int Hp;
    ArrayList<DraggableImage> hand;
    int pos=0;
    String Name;
    boolean autoPlay;
    private Robot robot;
    public Player(boolean bot, String name){
        autoPlay=bot;
        if(bot){
            try {
                robot= new Robot();
            } catch (AWTException e) {
                System.out.println("error in robot");
            }
        }
        Name=name;
        deck1= new Card[60];
        deck2= new Card[60];
        for(int i=1; i<=3; i++){
            String a = FileLoader.returnFileAsString("Deck"+i+".txt");
            String[] cardList = a.split("\",\"");
            for(int j=0; j<cardList.length; j++){
                String string= cardList[j];
                if (string.contains("name") || string.contains("Name")) {
                    Card c1;
                    if(string.contains("Type: Spell Card") || string.contains("Type: Trap Card") ){
                    c1 = new SpellTrap(string);
                    }else{
                    c1= new Monster(string);
                    }
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

    public void Play(MainWindow mw) throws InterruptedException{
        //setPlayerCardsInteractable(true);
        ImagePanel imagePanel= mw.imagePanel;
        ArrayList<Zone> myField= new ArrayList<>();
        ArrayList<Zone> oppField= new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            oppField.add(imagePanel.zones.get(i));
        }
        for (int i = 14; i < imagePanel.zones.size(); i++) {
            myField.add(imagePanel.zones.get(i));
        }
        if(mw.phase==4){
                draw(mw);
                ArrayList<DraggableImage> sorted =  evaluateHand();
                while (sorted.size()>0){
                    Card card=sorted.get(0).c;
                    if(card!=null){
                    Point p =sorted.get(0).getLocation();
                    robot.mouseMove(p.x+300,p.y+110);
                    if(card.level<=4){
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        for(int i=0; i < myField.size();i++){
                            Zone z=myField.get(i);
                            if(z.inUse==false && z.getName().equals(Name) && z.type.contains(card.ct)){
                                    p= z.getLocation();
                                    robot.mouseMove(p.x+300, p.y+110);
                                    Thread.sleep(250);
                                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                    break;
                            }
                        }
                    }else{
                        ArrayList<DraggableImage> fCards= new ArrayList<>();
                        for (int i=0; i<myField.size(); i++) {
                            Point q=myField.get(i).getLocation();
                            if(imagePanel.getDraggableImageAt(new Point(q.x, q.y))!=null && imagePanel.getDraggableImageAt(new Point(q.x, q.y)).c.ct.equals("Monster")){
                                fCards.add(imagePanel.getDraggableImageAt(new Point(q.x, q.y)));
                            }
                        }

                        //System.out.println(fCards.size() + " Selecting tributes");

                        if(card.level<7 & fCards.size()>=1){
                            int min=5000;
                            DraggableImage target=null;
                            for (int i=0; i<fCards.size(); i++){
                                if(fCards.get(i).c.atk<=min){
                                    min= fCards.get(i).c.atk;
                                    target=fCards.get(i);
                                }
                            }
                        if(min<card.atk && target!=null){
                                imagePanel.sendToGrave(target);
                                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                for(int i=0; i < myField.size();i++){
                                    Zone z=myField.get(i);
                                    if(z.inUse==false && z.getName().equals(Name) && z.type.contains(card.ct)){
                                            p= z.getLocation();
                                            robot.mouseMove(p.x+300, p.y+110);
                                            Thread.sleep(250);
                                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                            break;
                                    }
                                }
                            }
                        } else if(fCards.size()>1){
                            int min=5000;
                            DraggableImage target=null;
                            for (int i=0; i<fCards.size(); i++) {
                                if(fCards.get(i).c.atk<min){
                                    min= fCards.get(i).c.atk;
                                    target=fCards.get(i);
                                }
                            }
                            if(target!=null){
                            fCards.remove(target);
                            }
                            int min2=5000;
                            DraggableImage target2=null;
                            for (int i=0; i<fCards.size(); i++) {
                                if(fCards.get(i).c.atk<min2){
                                    min2= fCards.get(i).c.atk;
                                    target2=fCards.get(i);
                                }
                            }
                        if(min<card.atk && min2<card.atk && target!=null && target2!=null){
                            imagePanel.sendToGrave(target);
                            imagePanel.sendToGrave(target2);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                for(int i=0; i < myField.size();i++){
                                    Zone z=myField.get(i);
                                    if(z.inUse==false && z.getName().equals(Name) && z.type.contains(card.ct)){
                                            p= z.getLocation();
                                            robot.mouseMove(p.x+300, p.y+110);
                                            Thread.sleep(250);
                                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                            break;
                                    }
                                }
                            } 
                        }
                    }
                }else{System.out.println("No card chosen");}
                sorted.remove(0);
            }
            //System.out.println("Card was played");
            Thread.sleep(250);
            ArrayList<DraggableImage> fCards= new ArrayList<>();
                for (int i=0; i<myField.size(); i++) {
                    Point q=myField.get(i).getLocation();
                    if(imagePanel.getDraggableImageAt(new Point(q.x, q.y))!=null && imagePanel.getDraggableImageAt(new Point(q.x, q.y)).c.ct.equals("Monster")){
                        fCards.add(imagePanel.getDraggableImageAt(new Point(q.x, q.y)));
                        if(fCards.get(fCards.size()-1).rotated){
                            fCards.get(fCards.size()-1).rotateClockwise90();
                        }
                    }
                }
                //System.out.println(fCards.size()+" Checking field2");
                ArrayList<DraggableImage> oCards= new ArrayList<>();
                for (int i=0; i<oppField.size(); i++) {
                    Point q=oppField.get(i).getLocation();
                    if(imagePanel.getDraggableImageAt(new Point(q.x, q.y))!=null && imagePanel.getDraggableImageAt(new Point(q.x, q.y)).c.ct.equals("Monster")){
                        oCards.add(imagePanel.getDraggableImageAt(new Point(q.x, q.y)));
                    }
                }
                //System.out.println(oCards.size()+"Checking field1");
                for (int i = 0; i < fCards.size(); i++) {
                    if(oCards.size()==0){
                        fCards.get(i).dealDirect();
                    }
                    for (int index = 0; index < oCards.size(); index++) {
                        if(oCards.get(index).stat<fCards.get(i).stat){
                            imagePanel.battle(fCards.get(i), oCards.get(index));
                            break;
                        }
                        if(index==oCards.size()-1){
                            fCards.get(i).rotateClockwise90();
                        }
                    }
            }
            mw.phase=0;
        }
    }

    public ArrayList<DraggableImage> evaluateHand(){
        int max=0;
        ArrayList<DraggableImage> temp= new ArrayList<>();
        for (DraggableImage card : hand) {
            if(card.c.atk > max){
                max=card.c.atk;
                temp.add(0,card);
            }else{temp.add(card);}
        }
        return temp;
    }

    void draw(MainWindow mw){
        pos++;
        if(mDeck[pos]!=null){
        mw.card(mDeck[pos], Name);
        System.out.println(mDeck[pos].name);
        }else{
            Hp=0;
            if(Name.equals("Player1")){
                mw.lose();
            }else{
                mw.win();
            }
        }
    }

    public void setPlayerCardsInteractable(Boolean bool){
        for (int i = 0; i < hand.size(); i++) {
            hand.get(i).dragable=bool;
        }
    }

    public void changeHp(int change, MainWindow mw){
        Hp+=change;
        if(Hp<=0){
            if(Name.equals("Player1")){
                mw.lose();
            }else{
                mw.win();
            }
        }
    }
}
