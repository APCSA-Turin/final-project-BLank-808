package com.example;
import java.util.Scanner;
import java.util.jar.Attributes.Name;
import java.io.File;
import java.lang.StringBuilder;
public class Card{
    String fullData;
    String name,attribute,archetype,imageURL,imageURLS,type,race,id, desc;
    int atk,def,level,scale;
    public Card (String str, boolean addCardToList){
        fullData=reformat(str);
        fullData.replace(":", "\":\"");
        name= fullData.substring(fullData.indexOf("\"name\":\"")+8,fullData.indexOf("\",\"type"));
        type= fullData.substring(fullData.indexOf("\"type\":\"")+8,fullData.indexOf("\",\"h"));
        attribute= fullData.substring(fullData.indexOf("attribute\":\"")+12,(fullData.substring(fullData.indexOf("attribute\":\"")+12).indexOf("\"")+fullData.indexOf("attribute\":\"")+12));
        race= fullData.substring(fullData.indexOf("race\":\"")+7,(fullData.substring(fullData.indexOf("race\":\"")+7).indexOf("\"")+fullData.indexOf("race\":\"")+7));
        if(fullData.indexOf("archetype")>=0){
        archetype= fullData.substring(fullData.indexOf("archetype")+12,(fullData.substring(fullData.indexOf("archetype")+12).indexOf("\"")+fullData.indexOf("archetype")+12));
        }else{
        archetype="null";
        }
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
        desc=fullData.substring(fullData.indexOf("desc\":\"")+7,fullData.indexOf("\",\"race"));

         StringBuilder content= new StringBuilder();
         content.append("Name: " + name +"+");
         content.append("Level: " + level +"+");
         content.append("Type: " + type+" "+race +"+");
         content.append("Attribute: " + attribute +"+");
         content.append("Archetype: " + archetype +"+");
         content.append("Scale: " + scale +"+");
         content.append("Atk: " + atk +"+");
         content.append("Def: " + def +"+");
         content.append(desc);
         fullData=content.toString();

        if(addCardToList==true){
        FileSaver.saveData(fullData+"+ID: "+ id+"\n\",\"");
        // FileSaver.saveCardImage(imageURL, id);
        }
    }

    public Card(String savedDesc){
        String[] temp= savedDesc.split(": ");
        name= temp[1].substring(0,temp[1].indexOf("+"));
        level= Integer.valueOf(temp[2].substring(0,temp[2].indexOf("+")));
        type= temp[3].substring(0,temp[3].indexOf("+"));
        attribute= temp[4].substring(0,temp[4].indexOf("+"));
        archetype= temp[5].substring(0,temp[5].indexOf("+"));
        scale= Integer.valueOf(temp[6].substring(0,temp[6].indexOf("+")));
        atk= Integer.valueOf(temp[7].substring(0,temp[7].indexOf("+")));
        def= Integer.valueOf(temp[8].substring(0,temp[8].indexOf("+")));
        desc=temp[8].substring(temp[8].indexOf("+"), temp[8].indexOf("+ID"));
        id= temp[9];

        StringBuilder content= new StringBuilder();
         content.append("Name: " + name +"+");
         content.append("Level: " + level +"+");
         content.append("Type: " + type+" "+race +"+");
         content.append("Attribute: " + attribute +"+");
         content.append("Archetype: " + archetype +"+");
         content.append("Scale: " + scale +"+");
         content.append("Atk: " + atk +"+");
         content.append("Def: " + def +"+");
         content.append(desc);
         fullData=content.toString();
    }
    public String reformat(String str){
        String temp = str.replace( ",", "\n");
        temp = str.replace( "\\n", "\n");
        temp=temp.replace("\\r", "\n");
        id= temp.substring(temp.indexOf("\"id\":")+5,temp.indexOf(",\"name"));
        temp=temp.substring(temp.indexOf("\"n"), temp.indexOf("\"ygoprodeck_url")-1) +"\n" + temp.substring(temp.indexOf("image_url"), temp.indexOf("image_url_cropped"));
        return temp;
    }

    public String toString(){
        return fullData.replace("+", "\n");
    }

}