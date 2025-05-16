package com.example;
import javax.swing.JFrame;
class MainWindow {
    private JFrame window;
    
    public MainWindow(){
        window= new JFrame();
        window.setTitle("Very poorly done YU-GI-OH");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800, 500);
        window.setLocationRelativeTo(null);
    }

    public void show(){
        window.setVisible(true);
    }
}