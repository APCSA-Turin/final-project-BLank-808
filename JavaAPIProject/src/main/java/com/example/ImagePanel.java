package com.example;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;

public class ImagePanel extends JPanel{
    Point imagecorner;
    Point previusPoint;
    ImageIcon image;
    TextArea iArea;
    Card card;
    public ImagePanel(String path){
        image= new ImageIcon(path);
        image.setImage(image.getImage().getScaledInstance(160, 210, Image.SCALE_DEFAULT));
        imagecorner= new Point(0,0);
        ClickListener clickListener= new ClickListener();
        this.addMouseListener(clickListener);
        DragListener dragListener= new DragListener();
        this.addMouseMotionListener(dragListener);
    }

        public ImagePanel(String path, TextArea infoArea, Card c){
        image= new ImageIcon(path);
        iArea=infoArea;
        card=c;
        image.setImage(image.getImage().getScaledInstance(160, 210, Image.SCALE_DEFAULT));
        imagecorner= new Point(0,0);
        ClickListener clickListener= new ClickListener();
        this.addMouseListener(clickListener);
        DragListener dragListener= new DragListener();
        this.addMouseMotionListener(dragListener);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        System.out.println((int)imagecorner.getX() +" "+(int)imagecorner.getY());
        image.paintIcon(this, g, (int)imagecorner.getX(), (int)imagecorner.getY());
        if(iArea!=null){
            iArea.setText(card.toString());
        }
    }

    private class  ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent evt){
            previusPoint=evt.getPoint();
        }
    }

    private class  DragListener extends MouseMotionAdapter {
    
        public void mouseDragged(MouseEvent evt){
            if((int)evt.getX()<=image.getIconWidth()+imagecorner.getX() && (int)evt.getY()<=image.getIconHeight()+imagecorner.getY() && (int)evt.getX()>=imagecorner.getX() && (int)evt.getY()>=imagecorner.getY()){
            Point currPoint= evt.getPoint();
            imagecorner.translate((int)(currPoint.getX()-previusPoint.getX()), (int)(currPoint.getY()-previusPoint.getY()));
            previusPoint=currPoint;
            repaint();
            }
        }
    }
}
