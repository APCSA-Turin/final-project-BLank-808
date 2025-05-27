package com.example;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import java.awt.*;

class MainWindow extends JFrame{
    ImagePanel imagePanel;
    private JFrame window;
    public MainWindow(){
        window=new JFrame();
        setLayout(new BorderLayout());
        window.setTitle("Very poorly done YU-GI-OH");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(10000,10000);
        window.setLocationRelativeTo(null);
        WestPanel infoPanel= new WestPanel();
        window.add(infoPanel,BorderLayout.WEST);
        //adds Image panel with selected info panel
        imagePanel= new ImagePanel(infoPanel,this);
        window.add(imagePanel);
    }

    public void show(){
        window.setVisible(true);
    }

    public void card(Card card, String location){
        imagePanel.addCardImage("JavaAPIProject/src/main/media/"+card.id+".jpg",card,location);
    }
}