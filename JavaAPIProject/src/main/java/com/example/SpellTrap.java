package com.example;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.example.ImagePanel.DraggableImage;
import com.example.ImagePanel.Zone;

public class SpellTrap extends Card{
    int turnsSinceSet=0;
    Card target;
    ArrayList<Card> affected;
    boolean activated;
    int speed=1;
    public SpellTrap(String str){
        super(str,1);
        activated=false;
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

    public void activate(ImagePanel board){
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
       affected= new ArrayList<>();
        String Name= name;
        switch(Name){
            case "A Legendary Ocean":
                for (int i=0; i<list.size();i++) {
                    Card card=list.get(i);
                    if(card.ct.contains("Monster")){
                        if(card.attribute.equals("WATER")&& !affected.contains(card)){
                            card.atk+=200;
                            card.def+=200;
                            card.level-=1;
                            affected.add(card);
                            card.updateText();
                        }
                    }
                }
                for (DraggableImage card : hand) {
                    if(card.c.ct.contains("Monster")){
                        if(card.c.attribute.equals("WATER")&& !affected.contains(card.c)){
                            card.c.atk+=200;
                            card.c.def+=200;
                            card.c.level-=1;
                            affected.add(card.c);
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
                        if(card.attribute.equals("EARTH")&& !affected.contains(card)){
                            card.atk-=500;
                            card.def+=400;
                            affected.add(card);
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
                        if(card.type.contains("Rock")&& !affected.contains(card)){
                            card.atk+=500;
                            card.def+=500;
                            affected.add(card);
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
                        if(card.type.contains("Fiend")&& !affected.contains(card)){
                            card.atk+=500;
                            card.updateText();
                            affected.add(card);
                        }
                    }
                }
                activated=true;
                break;
            case "Celestia":
                for (int i=0; i<list.size();i++) {
                    Card card=list.get(i);
                    if(card.ct.contains("Monster")){
                        if(card.type.contains("Wyrm")&& !affected.contains(card)){
                            card.atk+=500;
                            affected.add(card);
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
                if(!activated){
                    if(owner.Name.equals("Player1")){
                        ArrayList<Card> onField= new ArrayList<>();
                        ArrayList<String> onFieldOptions= new ArrayList<>();
                        ArrayList<Zone> zones = board.zones;
                        for(int i=0; i<28; i++){
                            if(board.getDraggableImageAt(zones.get(i).getLocation())!=null && board.getDraggableImageAt(zones.get(i).getLocation()).c == this){
                            DraggableImage self= board.getDraggableImageAt(zones.get(i).getLocation());
                            self.changeViewable();
                            }
                            if(board.getDraggableImageAt(zones.get(i).getLocation())!=null && board.getDraggableImageAt(zones.get(i).getLocation()).c.ct.contains("Monster")){
                                    onField.add(board.getDraggableImageAt(zones.get(i).getLocation()).c);
                                    onFieldOptions.add(board.getDraggableImageAt(zones.get(i).getLocation()).c.name +" ("+board.getDraggableImageAt(zones.get(i).getLocation()).c.owner.Name+")");
                            }
                        }
                        if(onField.size()>0){
                        String[] targets = onFieldOptions.toArray(new String[onFieldOptions.size()]);
                        int tc = JOptionPane.showOptionDialog(
                        null,
                        "Select monster to tribute",
                        "Tribute prompt",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        targets,
                        targets[0] // Default option
                        );
                        target=onField.get(tc);
                        target.atk+=1000;
                        target.updateText();
                        activated=true;
                    }
                }
                }else{
                    target.owner.changeHp(-500, board.mw);
                    board.HpDisplay.setText(String.valueOf(board.p1.Hp));
                }
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
    }
}
