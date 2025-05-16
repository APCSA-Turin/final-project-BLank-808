package com.example;
import java.util.Scanner;
import java.lang.StringBuilder;
public class Card{
    String fullData;
    String name,attribute,archetype,imageURL,imageURLS,type, race;
    int atk,def,level,scale;
    public Card (String str, boolean addCardToList){
        fullData=reformat(str);
        name= fullData.substring(fullData.indexOf("\"name\":\"")+8,fullData.indexOf("\",\"t"));
        type= fullData.substring(fullData.indexOf("\"type\":\"")+8,fullData.indexOf("\",\"h"));
        attribute= fullData.substring(fullData.indexOf("attribute\":\"")+12,(fullData.substring(fullData.indexOf("attribute\":\"")+12).indexOf("\"")+fullData.indexOf("attribute\":\"")+12));
        race= fullData.substring(fullData.indexOf("race\":\"")+7,(fullData.substring(fullData.indexOf("race\":\"")+7).indexOf("\"")+fullData.indexOf("race\":\"")+7));
        archetype= fullData.substring(fullData.indexOf("archetype")+13,(fullData.substring(fullData.indexOf("archetype")+12).indexOf("\"")+fullData.indexOf("archetype")+12));
        imageURL= fullData.substring(fullData.indexOf("image_url\":\"")+12, fullData.indexOf("\",\"image_url_small\""));
        imageURLS= fullData.substring(fullData.indexOf("\",\"image_url_small\"")+21, fullData.length()-3);
        if(type.indexOf("Pendulum")>=0){
            scale= Integer.valueOf(fullData.substring(fullData.indexOf("\"scale\":")+8,fullData.indexOf("image_url")-1));
        }else{
            scale= -1;
        }
        if(fullData.indexOf("atk")>0){
         atk= Integer.valueOf(fullData.substring(fullData.indexOf("\"atk\":")+6, fullData.indexOf(",\"def\":")));
         def= Integer.valueOf(fullData.substring(fullData.indexOf("\"def\":")+6,fullData.indexOf(",\"level\":") ));
         level= Integer.valueOf(fullData.substring(fullData.indexOf("\"level\":")+8,fullData.indexOf(",\"attribute\":") ));
        }
         StringBuilder content= new StringBuilder();
         content.append("Name: " + name +"\n");
         content.append("Level: " + level +"\n");
         content.append("Type: " + type+" "+race +"\n");
         content.append("Attribute: " + attribute +"\n");
         content.append("Archetype: " + archetype +"\n");
         content.append("Scale: " + scale +"\n");
         content.append("Atk: " + atk +"\n");
         content.append("Def: " + def +"\n");
         content.append("Image URL: " + imageURL +"\n");
         content.append("Image URL Small: " + imageURLS);
         //fullData=content.toString();

                 if(addCardToList==true){
        FileSaver.saveData(fullData);
        }
    }

    public String reformat(String str){
        String temp = str.replace( ",", "\n");
        temp = str.replace( "\\n", "\n");
        temp=temp.substring(temp.indexOf("\"n"), temp.indexOf("\"ygoprodeck_url")-1) +"\n" + temp.substring(temp.indexOf("image_url"), temp.indexOf("image_url_cropped"));
        return temp;
    }

    public String toString(){
        return fullData;
    }

}