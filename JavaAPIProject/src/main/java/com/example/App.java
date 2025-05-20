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
        String a = API.getData("https://db.ygoprodeck.com/api/v7/cardinfo.php?type=Normal%20Monster");
        String[] cardList= a.split("coolstuffinc_price");
        for (String string : cardList) {
            if(string.contains("name")){
            Card c1= new Card(string, true);
            System.out.println(c1);
            }
        }
        // SwingUtilities.invokeLater(new Runnable() {
        //     @Override
        //     public void run(){
        //         MainWindow mW= new MainWindow();
        //         mW.card(c1);
        //         mW.show();
        //     }
        // });
    }
}
