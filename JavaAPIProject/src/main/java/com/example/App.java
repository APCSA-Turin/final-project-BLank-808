package com.example;
import org.json.JSONObject;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Card c1= new Card(API.getData("https://db.ygoprodeck.com/api/v7/cardinfo.php?name=Dark Magician"));
        System.out.println(c1);
    }
}
