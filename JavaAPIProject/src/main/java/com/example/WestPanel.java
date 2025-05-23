package com.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WestPanel extends JPanel{
    JTextArea iArea;
    JTextArea HpDisplay;

    WestPanel(){
        setLayout(new BorderLayout());
        HpDisplay=new JTextArea();
        HpDisplay.setText(String.valueOf(App.Hp));
        HpDisplay.setBackground(getBackground());
        HpDisplay.setFont(new Font("Base", 1,20));
        iArea= new JTextArea("Info");
        iArea.setBackground(Color.WHITE);
        iArea.setRows(15);
        iArea.setColumns(25);
        iArea.setLineWrap(true);
        iArea.setWrapStyleWord(true);
        iArea.setFont(new Font("Base", 1,13));
        iArea.setIgnoreRepaint(true);
        add(iArea,BorderLayout.WEST);
        add(HpDisplay, BorderLayout.NORTH);
    }
}
