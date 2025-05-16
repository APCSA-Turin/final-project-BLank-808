package com.example;
import org.json.JSONObject;
import javax.swing.SwingUtilities;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                MainWindow mW= new MainWindow();
                mW.show();
            }
        });
        Card c1= new Card( API.getData("https://db.ygoprodeck.com/api/v7/cardinfo.php?name=Tin Goldfish"), false);
        System.out.println();
        System.out.println(c1);
    }
}
