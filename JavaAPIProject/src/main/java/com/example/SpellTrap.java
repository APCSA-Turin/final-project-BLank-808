package com.example;

public class SpellTrap extends Card{
    int turnsSinceSet=0;
    int speed=1;
    public SpellTrap(String str){
        super(str,1);
        if (type.contains("spell")){
            ct="Spell";
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

    public void activate(){

    }
}
