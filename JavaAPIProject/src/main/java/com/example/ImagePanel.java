package com.example;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImagePanel extends JPanel{
    Point previusPoint;
    MainWindow mw;
    int handX=300;
    int cardHeight,cardWidth;
    ArrayList<DraggableImage> images= new ArrayList<>();
    ArrayList<Zone> zones= new ArrayList<>();
    Zone Graveyard1, Graveyard2;
    JTextArea iArea, HpDisplay;
    Point dragStartPoint;
    DraggableImage draggedImage;
    public ImagePanel(WestPanel ta, MainWindow wContainer){
        mw=wContainer;
        setLayout(null);
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        setBorder(lineBorder);
        iArea=ta.iArea;
        HpDisplay=ta.HpDisplay;
        int y=(int)(wContainer.getHeight()/1.55)-(cardHeight+10);
        cardHeight=(int)(wContainer.getHeight()* 0.116);
        cardWidth=(int)(wContainer.getWidth()*0.054);
        String currenttext="Spell/Trap Zone";
        String playerID="Player1";
        for(int i=0;i<28;i++){
            Zone jLabel=new Zone(currenttext);
            jLabel.setName(playerID);
            jLabel.setBounds(0, 0, cardWidth, cardHeight);
            jLabel.setBorder(lineBorder);
            add(jLabel);
            zones.add(jLabel);
            jLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            jLabel.setLocation(250+((i%7)*90), y);
            switch (i) {
                case 0:
                    jLabel.setType("Extra deck");
                    break;
                case 6:
                    y-= cardHeight+5;
                    currenttext="Monster zone";
                    jLabel.setType("Main deck");
                    break;
                    
                case 7:
                    jLabel.setType("Field Zone");
                    break;
                case 13:
                    y-= (2*cardHeight)+5;
                    playerID="Player2";
                    currenttext= "Monster zone";
                    jLabel.setType("Graveyard");
                    Graveyard1=jLabel;
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
                if(draggedImage.c!=null && draggedImage.interacable){
                    iArea.setText(draggedImage.c.toString());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                    DraggableImage other=getDraggableImageAt(e.getPoint(), draggedImage);
                    Zone zone=getZoneAt(e.getPoint());
                    if(other!=null && !draggedImage.getName().equals(other.getName())){
                        if(draggedImage.stat<other.stat){
                            App.Hp+= draggedImage.stat - other.stat;
                            if(!other.rotated){
                            remove(draggedImage);
                            images.remove(draggedImage);
                            getZoneAt(draggedImage.start).empty();
                            Graveyard1.contains.add(draggedImage.c);
                            }
                            HpDisplay.setText(String.valueOf(App.Hp));
                            if(App.Hp<=0){
                                wContainer.lose();
                            }
                        }else if(draggedImage.stat>other.stat){
                            App.Hp2+= draggedImage.stat - other.stat;
                            other.setSize(10,10);
                            remove(other);
                            images.remove(other);
                            zone.empty();
                            if(App.Hp2<=0){
                                wContainer.win();
                            }
                        }
                        repaint();
                    }
                if(zone!=null && zone.getName().equals(draggedImage.getName()) && zone.type.contains(draggedImage.c.ct)&& zone.inUse!=true){
                    if(getZoneAt(draggedImage.start)==null){
                        draggedImage.start=zone.getLocation();
                        zone.occupy();
                    }
                }
                draggedImage.setLocation(draggedImage.start);
                draggedImage = null;
            }

            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                draggedImage= getDraggableImageAt(e.getPoint());
                if(draggedImage!=null & draggedImage.interacable){
                draggedImage.rotateClockwise90(draggedImage.toBuffered());
                }
                System.out.println("Double clicked");
                }

                if(getZoneAt(e.getPoint()).type.equals("Graveyard")){
                    iArea.setText(getZoneAt(e.getPoint()).contains.toString());
                }
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
                image.setLocation(handX, (int)(mw.getHeight()-1.6*cardHeight));
                image.start=new Point(handX,(int)(mw.getHeight()-1.6*cardHeight));
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

        private Zone getZoneAt(Point point) {
        for (int i = zones.size() - 1; i >= 0; i--) {
            Zone zone = zones.get(i);
            if (zone.getBounds().contains(point)) {
                return zone;
            }
        }
        return null;
    }

    private class DraggableImage extends JLabel {
        Card c;
        Image front;
        boolean rotated=false;
        boolean interacable=true;
        boolean viewable;
        Point start;
        int stat;

        public DraggableImage(ImageIcon icon,Card card) {
            super(icon);
            front=icon.getImage();
            c=card;
            stat=c.atk;
        }
        
        public void rotateClockwise90(BufferedImage src) {
            int width = src.getWidth();
            int height = src.getHeight();
            BufferedImage dest = new BufferedImage(height, width, src.getType());

            Graphics2D graphics2D = dest.createGraphics();
            graphics2D.translate((height - width) / 2, (height - width) / 2);
            if(rotated==false){
            graphics2D.rotate(Math.PI / 2, height / 2.0, width / 2.0);
            rotated=true;
            stat=c.def;
            }else{
            for(int i=0; i<3;i++){
            graphics2D.rotate(Math.PI / 2, height / 2.0, width / 2.0);
            ((ImageIcon)getIcon()).setImage(dest);
            width = toBuffered().getWidth();
            height = toBuffered().getHeight();
            }
            rotated=false;
            stat=c.atk;
            }
            graphics2D.drawRenderedImage(src, null);
            ((ImageIcon)getIcon()).setImage(dest);
            repaint();
        }

        private BufferedImage toBuffered(){{
            Image img= ((ImageIcon)getIcon()).getImage();

            // Create a buffered image with transparency
            BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();

            // Return the buffered image
            return bimage;
            }
        }

        public void changeViewable(){
            if(viewable){
                viewable=false;
                ((ImageIcon)getIcon()).setImage(Card.back);
            }else{
                viewable=true;
                ((ImageIcon)getIcon()).setImage(front);
            }
        }
    }

    private class Zone extends JLabel{
        String type;
        boolean inUse;
        ArrayList<Card> contains;
        public Zone(String type){
            super(type);
            this.type=type;
            inUse=false;
        }

        public void setType(String str){
            setText(str);
            type=str;
            if(type.equals("Graveyard") || type.contains("deck")){
                inUse=true;
                contains=new ArrayList<>();
            }
        }

        public void occupy(){
            inUse=true;
            setText(" ");
        }

        public void empty(){
            inUse=false;
            setText(type);
        }
    }
}