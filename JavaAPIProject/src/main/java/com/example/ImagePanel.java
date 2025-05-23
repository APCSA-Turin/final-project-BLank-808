package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class ImagePanel extends JPanel{
    Point previusPoint;
    ArrayList<DraggableImage> images= new ArrayList<>();
    JTextArea iArea;
    JTextArea HpDisplay;
    Point dragStartPoint;
    DraggableImage draggedImage;
    public ImagePanel(WestPanel ta, Container wContainer){
        iArea=ta.iArea;
        HpDisplay=ta.HpDisplay;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStartPoint = e.getPoint();
                draggedImage = getDraggableImageAt(dragStartPoint);
                if(draggedImage.c!=null){
                    iArea.setText(draggedImage.c.toString());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedImage = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedImage != null) {
                    Point currentPoint = e.getPoint();
                    int deltaX = currentPoint.x - dragStartPoint.x;
                    int deltaY = currentPoint.y - dragStartPoint.y;

                    draggedImage.setLocation(draggedImage.getX() + deltaX, draggedImage.getY() + deltaY);
                    dragStartPoint = currentPoint;
                    DraggableImage other=getDraggableImageAt(currentPoint);
                    if(draggedImage != other){
                        if(draggedImage.c.atk<other.c.atk){
                            App.Hp+= draggedImage.c.atk - other.c.atk;
                            remove(draggedImage);
                            draggedImage=null;
                            HpDisplay.setText(String.valueOf(App.Hp));
                        }else if(draggedImage.c.atk>other.c.atk){
                            App.Hp2+= draggedImage.c.atk - other.c.atk;
                            other.setSize(10,10);
                            remove(other);
                        }
                    }
                }
            }
        });

        setVisible(true);
    }

    private void addImage(String imagePath, int x, int y) {
        try{
            ImageIcon icon = new ImageIcon(imagePath);
            icon.setImage(icon.getImage().getScaledInstance(160, 210, Image.SCALE_DEFAULT));
            DraggableImage image = new DraggableImage(icon);
            image.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
            add(image);
            images.add(image);
            repaint();
        } catch (Exception e){
            System.err.println("Error loading or adding image: " + e.getMessage());
        }
    }

    public void addCardImage(String imagePath, Card c) {
        try{
            ImageIcon icon = new ImageIcon(imagePath);
            icon.setImage(icon.getImage().getScaledInstance(116, 140, Image.SCALE_DEFAULT));
            DraggableImage image = new DraggableImage(icon,c);
            image.setBounds(0,0,icon.getIconWidth(), icon.getIconHeight());
            add(image);
            images.add(image);
            repaint();
        } catch (Exception e){
            System.err.println("Error loading or adding image: " + e.getMessage());
        }
    }

    private DraggableImage getDraggableImageAt(Point point) {
        for (int i = images.size() - 1; i >= 0; i--) {
            DraggableImage image = images.get(i);
            if (image.getBounds().contains(point)) {
                return image;
            }
        }
        return null;
    }

    private class DraggableImage extends JLabel {
        Card c;
        public DraggableImage(ImageIcon icon) {
            super(icon);
        }

        public DraggableImage(ImageIcon icon,Card card) {
            super(icon);
            c=card;
        }
    }
}