package com.example;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.*;

class MainWindow extends JFrame{
    private JFrame window;
    private ImagePanel imagePanel;
    TextArea infoPanel;
    public MainWindow(){
        window= new JFrame();
        window.setLayout(new BorderLayout());
        window.setTitle("Very poorly done YU-GI-OH");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(10000,10000);
        window.setLocationRelativeTo(null);
        infoPanel= new TextArea("Info");
        imagePanel= new ImagePanel(infoPanel);
        window.add(imagePanel);
        infoPanel.setEditable(false);
        window.add(infoPanel, BorderLayout.WEST);
    }

    public void show(){
        window.setVisible(true);
    }

    public void card(Card card, String location, int x, int y){
        imagePanel.addCardImage("JavaAPIProject/src/main/media/"+card.id+".jpg",x,y,card);
    }
}