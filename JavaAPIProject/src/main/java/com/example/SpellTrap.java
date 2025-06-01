package com.example;

import java.util.ArrayList;

import com.example.ImagePanel.DraggableImage;

public class SpellTrap extends Card{
    int turnsSinceSet=0;
    boolean activated;
    int speed=1;
    public SpellTrap(String str){
        super(str,1);
        if (type.contains("Spell")){
            ct= "Spell";
            if(type.contains(" Field")){
                ct= "Field";
            }
            if(type.contains("Quick")){
                speed=2;
            }
        }else{
            ct="Trap";
            speed=2;
            if(type.contains("Counter")){
                speed=3;
            }
        }
    }

    public boolean activate(ImagePanel board){
        ArrayList<Card> list= board.field; 
        ArrayList<DraggableImage> hand;
        Card[] Deck;
        if(owner.Name.equals("Player 1")){
        hand=board.hand1;
        Deck=board.p1.mDeck;
        }else{
        hand=board.hand2;
        Deck=board.p2.mDeck;
        }
        ArrayList<Card> cardsaffected= new ArrayList<>();
        String Name= name;
        switch(Name){
            case "A Legendary Ocean":
                for (int i=0; i<list.size();i++) {
                    Card card=list.get(i);
                    if(card.ct.contains("Monster")){
                        if(card.attribute.equals("WATER")&& !cardsaffected.contains(card)){
                            card.atk+=200;
                            card.def+=200;
                            card.level-=1;
                            cardsaffected.add(card);
                            card.updateText();
                        }
                    }
                }
                for (DraggableImage card : hand) {
                    if(card.c.ct.contains("Monster")){
                        if(card.c.attribute.equals("WATER")&& !cardsaffected.contains(card.c)){
                            card.c.atk+=200;
                            card.c.def+=200;
                            card.c.level-=1;
                            cardsaffected.add(card.c);
                            card.c.updateText();
                        }
                    }
                }
                activated=true;
                break;
            case "Acidic Downpour":
                for (int i=0; i<list.size();i++) {
                    Card card=list.get(i);
                    if(card.ct.contains("Monster")){
                        if(card.attribute.equals("EARTH")&& !cardsaffected.contains(card)){
                            card.atk-=500;
                            card.def+=400;
                            cardsaffected.add(card);
                            card.updateText();
                        }
                    }
                }
                activated=true;
                break;
            case "Adamancipator Laputite":
                for (int i=0; i<list.size();i++) {
                    Card card=list.get(i);
                    if(card.ct.contains("Monster")){
                        if(card.type.contains("Rock")&& !cardsaffected.contains(card)){
                            card.atk+=500;
                            card.def+=500;
                            cardsaffected.add(card);
                            card.updateText();
                        }
                    }
                }
                activated=true;
                break;
            case "Archfiend Palabyrinth":
                for (int i=0; i<list.size();i++) {
                    Card card=list.get(i);
                    if(card.ct.contains("Monster")){
                        if(card.type.contains("Fiend")&& !cardsaffected.contains(card)){
                            card.atk+=500;
                            System.out.println(card.atk);
                            card.updateText();
                            cardsaffected.add(card);
                            System.out.println(card);
                            System.out.println("************************************");
                        }
                    }
                }
                activated=true;
                break;
            case "Celestia":
                for (int i=0; i<list.size();i++) {
                    Card card=list.get(i);
                    if(card.ct.contains("Monster")){
                        if(card.type.contains("Wyrm")&& !cardsaffected.contains(card)){
                            card.atk+=500;
                            cardsaffected.add(card);
                            card.updateText();
                        }
                    }
                }
                activated=true;
                break;
            case "Ignition Phoenix":
                break;
            case "Jurassic World":
                break;
            case "Lost World":
                break;
            case "Luminous Spark":
                break;
            case "Mountain":
                break;
            case "Rising Air Current":
                break;
            case "Mystic Plasma Zone":
                break;
            case "Temple of the Mind's Eye":
                break;
            case "The Sanctuary in the Sky":
                break;
            case "Action Magic - Double Banking":
                break;
            case "Action Magic - Full Turn":
                break;
            case "Beast Fangs":
                break;
            case "Break! Draw!":
                break;
            case "Mirror ForceAdd commentMore actions":
                break;
            case "Spell Absorption":
                break;
            case "De-Spell":
                break;
            case "Treacherous Trap Hole":
                break;
            case "Trap Hole of Spikes":
                break;
            case "Trap Hole":
                break;
            case "Quaking Mirror Force":
                break;
            case "Storming Mirror Force":
                break;
            case "Drowning Mirror Force":
                break;
            case "Broken Bamboo Sword":
                break;
            case "Book of Secret Arts":
                break;
            case "Book of Lunar Eclipse":
                break;
            case "Book of Moon":
                break;
            case "Birthright":
                break;
            case "Banner of Courage":
                break;
            case "Backup Squad":
                break;
            case "Backfire":
                break;
            case "Axe of Fools":
                break;
            case "Axe of Despair":
                break;
            case "Astral Barrier":
                break;
            case "Arrivalrivals":
                break;
            case "Archfiend's Staff of Despair":
                break;
            case "Anti-Magic Arrows":
                break;
            case "7":
                break;
            case "7 Completed":
                break;
            case "Yami":
                break;
            case "Wetlands":
                break;
            case "Wasteland":
                activated=true;
                break;
            case "Umiiruka":
                activated=true;
                break;
            case "Umi":
                activated=true;
                break;
        }
        return activated;
    }
}
