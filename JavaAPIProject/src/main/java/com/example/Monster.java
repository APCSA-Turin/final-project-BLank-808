package com.example;

public class Monster extends Card{
    Boolean pendulum=false;
    public Monster(String str){
        super(str);
        if(scale!=-1){
            pendulum=true;
        }
    }


}
