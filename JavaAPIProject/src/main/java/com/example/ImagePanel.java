package com.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class ImagePanel extends JPanel{
    Point previusPoint;
    int handX=300;
    int cardHeight,cardWidth;
    ArrayList<DraggableImage> images= new ArrayList<>();
    ArrayList<JLabel> zones= new ArrayList<>();
    JTextArea iArea;
    JTextArea HpDisplay;
    Point dragStartPoint;
    DraggableImage draggedImage;
    public ImagePanel(WestPanel ta, MainWindow wContainer){
        setLayout(null);
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        setBorder(lineBorder);
        iArea=ta.iArea;
        HpDisplay=ta.HpDisplay;
        int y=(int)(wContainer.getHeight()/1.55)-(cardHeight+10);
        System.out.println(wContainer.getHeight());
        cardHeight=(int)(wContainer.getHeight()* 0.116);
        System.out.println(wContainer.getWidth());
        cardWidth=(int)(wContainer.getWidth()*0.054);
        String currenttext="Spell/Trap Zone";
        String playerID="Player1";
        for(int i=0;i<28;i++){
            JLabel jLabel=new JLabel(currenttext);
            jLabel.setName(playerID);
            jLabel.setBounds(0, 0, 83, 100);
            jLabel.setBorder(lineBorder);
            add(jLabel);
            zones.add(jLabel);
            jLabel.setLocation(250+((i%7)*90), y);
            switch (i) {
                case 6:
                    y-= cardHeight+5;
                    currenttext="Monster zone";
                    break; 
                case 13:
                    y-= (2*cardHeight)+5;
                    playerID="Player2";
                    currenttext= "Monster zone";
                    break;
                case 20:
                    y-= cardHeight+5;
                    currenttext = "Spell/Trap Zone";
                    break;
                default:
                    break;
            }
        }

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
                    DraggableImage other=getDraggableImageAt(e.getPoint(), draggedImage);
                    System.out.println(other);
                    if(other!=null && !draggedImage.getName().equals(other.getName())){
                        if(draggedImage.c.atk<other.c.atk){
                            App.Hp+= draggedImage.c.atk - other.c.atk;
                            remove(draggedImage);
                            images.remove(draggedImage);
                            HpDisplay.setText(String.valueOf(App.Hp));
                            if(App.Hp<=0){
                                wContainer.lose();
                            }
                        }else if(draggedImage.c.atk>other.c.atk){
                            App.Hp2+= draggedImage.c.atk - other.c.atk;
                            other.setSize(10,10);
                            remove(other);
                            images.remove(other);
                            if(App.Hp2<=0){
                                wContainer.win();
                            }
                        }
                        repaint();
                    }
                JLabel zone=getZoneAt(e.getPoint());
                if(zone!=null && zone.getName().equals(draggedImage.getName())){
                        draggedImage.start=getZoneAt(e.getPoint()).getLocation();
                }
                draggedImage.setLocation(draggedImage.start);
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
                }
            }
        });
        setVisible(true);
    }

    public void reset(){
        for (int i = images.size() - 1; i >= 0; i--) {
            remove(images.get(0));
        }
        App.Hp=8000;
        HpDisplay.setText("8000");
        iArea.setText("Info");
        App.Hp2=8000;
        repaint();
    }

    public void addCardImage(String imagePath, Card c, String location) {
        try{
            ImageIcon icon = new ImageIcon(imagePath);
            icon.setImage(icon.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT));
            DraggableImage image = new DraggableImage(icon,c);
            image.setBounds(0,0,icon.getIconWidth(), icon.getIconHeight());
            add(image);
            images.add(image);
            repaint();
            if(location.equals("hand")){
                image.setLocation(handX, 690);
                image.start=new Point(handX,690);
                image.setName("Player1");
                handX+=cardWidth;
            }
            if(location.equals("handE")){
                image.setLocation(handX, -10);
                image.start=new Point(handX,-10);
                image.setName("Player2");
                handX+=cardWidth;
            }
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

        private DraggableImage getDraggableImageAt(Point point, DraggableImage exeption) {
        for (int i = images.size() - 1; i >= 0; i--) {
            DraggableImage image = images.get(i);
            if (image.getBounds().contains(point) && image != exeption) {
                return image;
            }
        }
        return null;
    }

        private JLabel getZoneAt(Point point) {
        for (int i = zones.size() - 1; i >= 0; i--) {
            JLabel zone = zones.get(i);
            if (zone.getBounds().contains(point)) {
                return zone;
            }
        }
        return null;
    }

    private class DraggableImage extends JLabel {
        Card c;
        Point start;
        public DraggableImage(ImageIcon icon) {
            super(icon);
        }

        public DraggableImage(ImageIcon icon,Card card) {
            super(icon);
            c=card;
        }
    }
}