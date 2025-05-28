package com.example;
import javax.swing.*;
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(screenSize);
        setSize(screenSize);
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
        JOptionPane.showMessageDialog(window, "You're just a third-rate duelist with a fourth-rate deck!", "You lose", JOptionPane.ERROR_MESSAGE);
    }

    public void card(Card card, String location){
        imagePanel.addCardImage("JavaAPIProject/src/main/media/"+card.id+".jpg",card,location);
    }
}