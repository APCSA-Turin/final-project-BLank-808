package com.example;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
class MainWindow extends JFrame{
    private JFrame window;
    TextArea infoPanel;
    
    public MainWindow(){
        window= new JFrame();
        window.setLayout(new BorderLayout());
        window.setTitle("Very poorly done YU-GI-OH");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(10000,10000);
        window.setLocationRelativeTo(null);
        infoPanel= new TextArea("Info");
        infoPanel.setEditable(false);
        window.add(infoPanel, BorderLayout.WEST);
    }

    public void show(){
        window.setVisible(true);
    }

    public void card(Card card){
        ImagePanel imagePanel= new ImagePanel("JavaAPIProject\\src\\main\\media\\"+card.id+".jpg", infoPanel, card);
        window.add(imagePanel);
        if(!window.isVisible()){
            window.setVisible(true);
            System.out.println("error");
        }
    }
}