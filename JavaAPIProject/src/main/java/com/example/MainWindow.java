package com.example;
import javax.swing.*;

import com.example.ImagePanel.DraggableImage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


class MainWindow extends JFrame{
    ImagePanel imagePanel;
    Player bot,player;
    int phase;
    private JFrame window;
    public MainWindow(Player p1, Player p2){
        window=new JFrame();
        player=p1;
        bot=p2;
        setLayout(new BorderLayout());
        window.setTitle("Very poorly done YU-GI-OH");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(screenSize);
        setSize(screenSize);
        window.setLocationRelativeTo(null);
        WestPanel infoPanel= new WestPanel();
        window.add(infoPanel,BorderLayout.WEST);
        //adds Image panel with selected info panel
        imagePanel= new ImagePanel(infoPanel,this, p1,p2);
        window.add(new NorthPanel(), BorderLayout.NORTH);
        window.add(imagePanel);
    }

    public void start(){
        window.setVisible(true);
        phase=0;
    }

    public void lose(){
        JOptionPane.showMessageDialog(window, "You're just a third-rate duelist with a fourth-rate deck!", "You lose", JOptionPane.ERROR_MESSAGE);
        String[] options = {"Retry", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Do you want to retry?",
                "Error",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                options,
                options[1] // Default option
        );

        if (choice == 0) {
            imagePanel.reset();
        } else {

        }
    }

    public void win(){
        JOptionPane.showMessageDialog(window, "You're quite the smart one!", "You Win", JOptionPane.ERROR_MESSAGE);
    }

    public void card(Card card, String location){
        imagePanel.addCardImage("JavaAPIProject/src/main/media/"+card.id+".jpg",card,location);
    }

    private class NorthPanel extends JPanel{

            public NorthPanel(){
                Button b= new Button("Main Phase 1");
                b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(phase<1){
                        phase=1;
                        player.draw(imagePanel.mw);
                        imagePanel.reactivate("Player1");
                        b.setBackground(Color.LIGHT_GRAY);
                    }
                }
            });
            add(b);
            Button b2= new Button("Battle Phase");
                b2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(phase<2){
                        phase=2;
                        b2.setBackground(Color.LIGHT_GRAY);
                        b.setBackground(Color.WHITE);
                    }
                }
            });
            add(b2);
            Button b3= new Button("Main Phase 2");
                b3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(phase<3){
                        phase=3;
                        b3.setBackground(Color.LIGHT_GRAY);
                        b2.setBackground(Color.WHITE);
                        b.setBackground(Color.WHITE);
                    }
                }
            });
            add(b3);
            Button b4= new Button("End Turn");
                b4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(phase<4){
                        phase=4;
                        try {
                            bot.Play(imagePanel.mw);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        b3.setBackground(Color.WHITE);
                        b2.setBackground(Color.WHITE);
                        b.setBackground(Color.WHITE);
                    }
                }
            });
            add(b4);
        }
    }
}