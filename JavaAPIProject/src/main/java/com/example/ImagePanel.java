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
    Player p1,p2;
    MainWindow mw;
    int cardHeight,cardWidth;
    ArrayList<DraggableImage> images,hand1,hand2;
    ArrayList<Zone> zones= new ArrayList<>();
    Zone Graveyard1, Graveyard2, Field1, Field2;
    JTextArea iArea, HpDisplay;
    ArrayList<Card> field;
    Point dragStartPoint;
    DraggableImage draggedImage;
    public ImagePanel(WestPanel ta, MainWindow wContainer, Player p1, Player p2){
        mw=wContainer;
        this.p1=p1;
        this.p2=p2;
        images= new ArrayList<>();

        hand1= new ArrayList<>();
        hand2= new ArrayList<>();
        p2.hand=hand2;
        p1.hand=hand1;
        field= new ArrayList<>();
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
                    jLabel.setIcon(new ImageIcon(Card.back.getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT)));
                    break;
                    
                case 7:
                    jLabel.setType("Field Zone");
                    Field1=jLabel;
                    break;
                case 13:
                    y-= (2*cardHeight)+5;
                    playerID="Player2";
                    currenttext= "Monster Zone";
                    jLabel.setType("Graveyard");
                    Graveyard1=jLabel;
                    jLabel.setIcon(new ImageIcon(Card.back.getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT)));
                    break;
                case 14:
                    jLabel.setType("Field Zone");
                    Field2=jLabel;
                    break;
                case 20:
                    y-= cardHeight+5;
                    currenttext = "Spell/Trap Zone";
                    jLabel.setType("Graveyard");
                    Graveyard2=jLabel;
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
                if(draggedImage!=null){
                    if(draggedImage.c!=null && draggedImage.viewable){
                        iArea.setText(draggedImage.c.toString());
                    }
                    if(!draggedImage.dragable){
                        draggedImage=null;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(draggedImage!=null){
                    DraggableImage other=getDraggableImageAt(e.getPoint(), draggedImage);
                    if(other!=null && getZoneAt(other.start)==null){
                        other=null;
                    }
                        Zone zone=getZoneAt(e.getPoint());
                        if(other!=null && !draggedImage.getName().equals(other.getName()) && other.interacable){
                            battle(draggedImage,other);
                        }
                        if(mw.phase==2 && OponentFieldEmpty()){
                            draggedImage.dealDirect();
                        }
                    if(zone!=null && zone.getName().equals(draggedImage.getName()) && zone.type.contains(draggedImage.c.ct)&& (zone.inUse!=true|| draggedImage.c.level>4)){
                        if(getZoneAt(draggedImage.start)==null){
                            if(draggedImage.c.level>4 && draggedImage.c.owner==p1){
                                draggedImage.summonable=false;
                                    String[] options = {"yes", "no"};
                                    int choice = JOptionPane.showOptionDialog(
                                        null,
                                        "Level over 4, tribute summon?",
                                        "Tribute prompt",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.ERROR_MESSAGE,
                                        null,
                                        options,
                                        options[1] // Default option
                                );

                                if (choice == 0) {
                                    ArrayList<DraggableImage> onField= new ArrayList<>();
                                    ArrayList<String> onFieldOptions= new ArrayList<>();
                                    for(int i=0; i<14; i++){
                                        if(getDraggableImageAt(zones.get(i).getLocation(),draggedImage)!=null && getDraggableImageAt(zones.get(i).getLocation()).c.ct.contains("Monster")){
                                            {
                                            onField.add(getDraggableImageAt(zones.get(i).getLocation()));
                                            onFieldOptions.add(getDraggableImageAt(zones.get(i).getLocation()).c.name);
                                            }
                                        }
                                    }
                                    onFieldOptions.add("No choice");
                                    String[] targets = onFieldOptions.toArray(new String[onFieldOptions.size()]);
                                    int tc = JOptionPane.showOptionDialog(
                                        null,
                                        "Select monster to tribute",
                                        "Tribute prompt",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.ERROR_MESSAGE,
                                        null,
                                        targets,
                                        targets[0] // Default option
                                    );
                                    if(tc<onField.size()){
                                        DraggableImage target=onField.get(tc);
                                        onField.remove(tc);
                                        onFieldOptions.remove(tc);
                                        targets = onFieldOptions.toArray(new String[onFieldOptions.size()]);
                                        if(draggedImage.c.level>6){
                                            tc = JOptionPane.showOptionDialog(
                                            null,
                                            "select second monster",
                                            "Tribute prompt",
                                            JOptionPane.YES_NO_OPTION,
                                            JOptionPane.ERROR_MESSAGE,
                                            null,
                                            targets,
                                            targets[0] // Default option
                                            );

                                            if(tc<onField.size()){
                                                draggedImage.start=target.start;
                                                sendToGrave(target);
                                                target=onField.get(tc);
                                                sendToGrave(target);
                                                draggedImage.summonable=true;
                                            }
                                        }else{
                                        draggedImage.start=target.start;
                                        sendToGrave(target);
                                        draggedImage.summonable=true;
                                        }
                                    }
                                }
                            }
                            if(draggedImage.summonable){
                                if(!zone.inUse){
                                draggedImage.start=zone.getLocation();
                                zone.occupy();
                                }else{
                                    getZoneAt(draggedImage.start).occupy();
                                }
                                draggedImage.dragable=false;
                                if(draggedImage.getName().equals("Player1")){
                                    hand1.remove(draggedImage);
                                }else{
                                    hand2.remove(draggedImage);
                                }
                                field.add(draggedImage.c);
                                if(!draggedImage.viewable){
                                    draggedImage.changeViewable();
                                }
                                if(draggedImage.c.ct.equals("Spell") || draggedImage.c.ct.equals("Trap")){
                                    draggedImage.changeViewable();
                                    draggedImage.viewable=false;
                                }
                                if(draggedImage.c.ct.equals("Field")){
                                    SpellTrap sp= (SpellTrap)draggedImage.c;
                                    System.out.println(sp.name + " was activated");
                                    sp.activate(mw.imagePanel);
                                }
                            }
                        }
                    }
                    if(draggedImage.start!=null){
                    draggedImage.setLocation(draggedImage.start);
                    draggedImage = null;
                    repaint();
                    }else{
                        System.out.println("No start point set");
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    draggedImage= getDraggableImageAt(e.getPoint());
                    if(draggedImage!=null & draggedImage.rotatable){
                        draggedImage.rotateClockwise90();
                    }
                    System.out.println(draggedImage.c);
                    if(draggedImage.c.ct.equals("Spell")){
                        ((SpellTrap)(draggedImage.c)).activate(mw.imagePanel);
                    }
                }
                if(getZoneAt(e.getPoint())!=null && getZoneAt(e.getPoint()).type.equals("Graveyard")){
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

    private boolean OponentFieldEmpty() {
                for (Card card : field) {
                    if(card.owner!=p1 && card.ct.equals("Monster")){
                        return false;
                    }
                }
                return true;
            }

    public void battle(DraggableImage d1, DraggableImage d2){
        Card FieldSpell1 = null,FieldSpell2 = null;
        if(getDraggableImageAt(Field1.getLocation())!=null){
            FieldSpell1=getDraggableImageAt(Field1.getLocation()).c;
        }
        if(getDraggableImageAt(Field2.getLocation())!=null){
        FieldSpell2=getDraggableImageAt(Field2.getLocation()).c;
        }
        d1.rotateClockwise90();
        d1.rotateClockwise90();
        d2.rotateClockwise90();
        d2.rotateClockwise90();
        if(d1.stat<d2.stat){
            if(!d2.rotated){
                sendToGrave(d1);
            }
            d1.c.owner.Hp+=d1.stat-d2.stat;
            if((FieldSpell1!=null &&FieldSpell1.name.equals("Canyon"))||(FieldSpell2!=null &&FieldSpell2.name.equals("Canyon"))){
                if(d2.rotated){
                d1.c.owner.Hp-=d2.stat-d1.stat;
                }
            } 
        }else if (d1.stat>d2.stat){
            sendToGrave(d2);
            d2.c.owner.Hp-=d1.stat-d2.stat;
        }
        d1.atksavaiable--;
        if(d1.atksavaiable<=0){
            d1.dragable=false;
            d1.rotatable=false;
        }
        System.out.println(d1.dragable);
        HpDisplay.setText(String.valueOf(p1.Hp));
        repaint();
    }

    public void reset(){
        for (int i = images.size() - 1; i >= 0; i--) {
            if(getZoneAt(images.get(0).getLocation())!=null){
            getZoneAt(images.get(0).getLocation()).empty();
            }
            remove(images.get(0));
            images.remove(0);
        }
        hand1.clear();
        hand2.clear();
        p1.Hp=8000;
        p1.pos=0;
        App.shuffle(p1.mDeck);
        for (int i = 0; i < 5; i++) {
        p1.draw(mw);   
        }
        HpDisplay.setText("8000");
        iArea.setText("Info");
        p2.Hp=8000;
        p2.pos=0;
        App.shuffle(p2.mDeck);
        for (int i = 0; i < 5; i++) {
        p2.draw(mw);   
        }
        repaint();
    }

    @Override
    public void repaint(){
        if(hand1!=null){
            for(DraggableImage di: hand1){
                di.setLocation(hand1.indexOf(di)*cardWidth+250,(int)di.start.getY());
            }
            for(DraggableImage di: hand2){
                di.setLocation(hand2.indexOf(di)*cardWidth+250,(int)di.start.getY());
            }
        }
        super.repaint();
    }

    public void addCardImage(String imagePath, Card c, String player) {
        try{
            ImageIcon icon = new ImageIcon(imagePath);
            icon.setImage(icon.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT));
            DraggableImage image = new DraggableImage(icon,c);
            image.setBounds(0,0,icon.getIconWidth(), icon.getIconHeight());
            add(image);
            images.add(image);
            if(player.equals("Player1")){
                hand1.add(image);
                int handX = ((hand1.size()-1)*cardWidth)+250;
                image.start=new Point(handX,(int)(mw.getHeight()-1.6*cardHeight));
                image.setLocation(handX, (int)(mw.getHeight()-1.6*cardHeight));
                c.owner=p1;
                image.setName("Player1");
            }
            if(player.equals("Player2")){
                hand2.add(image);
                c.owner=p2;
                int handX = ((hand2.size()-1)*cardWidth)+250;
                image.start=new Point(handX,-10);
                image.setLocation(handX, -10);
                image.setName("Player2");
                image.changeViewable();
            }
        } catch (Exception e){
            System.err.println("Error loading or adding image: " + e.getMessage());
        }
    }

    public DraggableImage getDraggableImageAt(Point point) {
        for (int i = images.size() - 1; i >= 0; i--) {
            DraggableImage image = images.get(i);
            if (image.getBounds().contains(point)) {
                return image;
            }
        }
        return null;
    }

    private DraggableImage getDraggableImageAt(Point point, DraggableImage exception ) {
        for (int i = images.size() - 1; i >= 0; i--) {
            DraggableImage image = images.get(i);
            if (image.getBounds().contains(point) && image != exception) {
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

    void setPlayerinteractable(String player){
        for (int i = 0; i < images.size(); i++) {
            if(images.get(i).getName().equals(player)){
                System.out.println(images.get(i).getName());
                if(mw.phase==0){
                    images.get(i).dragable=false;
                }else if(mw.phase==1){
                    if(!field.contains(images.get(i).c)){
                    images.get(i).dragable=true;
                    }
                }else if(mw.phase==2){
                    if(!field.contains(images.get(i).c)){
                    images.get(i).dragable=false;
                    }else{
                    images.get(i).dragable=true;
                    }
                }else if(mw.phase==3){
                    if(!field.contains(images.get(i).c)){
                    images.get(i).dragable=true;
                    }
                }else if(mw.phase==4){
                    images.get(i).dragable=true;
                }
            }
        }
    }

    public class DraggableImage extends JLabel {
        protected boolean summonable=true;
        Card c;
        Image front;
        boolean rotated=false;
        boolean interacable=true;
        boolean dragable;
        boolean rotatable=true;
        boolean viewable=true;
        Point start;
        int stat,atksavaiable;

        public DraggableImage(ImageIcon icon,Card card) {
            super(icon);
            front=icon.getImage();
            c=card;
            stat=c.atk;
            atksavaiable=1;
            if(!c.ct.contains("Monster")){
                rotatable=false;
            }
        }
        
        public void rotateClockwise90() {
            BufferedImage src=toBuffered();
            int width = src.getWidth();
            int height = src.getHeight();
            BufferedImage dest = new BufferedImage(height, width, src.getType());

            Graphics2D graphics2D = dest.createGraphics();
            graphics2D.translate((height - width) / 2, (height - width) / 2);
            if(rotated==false){
            graphics2D.rotate(Math.PI / 2, height / 2.0, width / 2.0);
            rotated=true;
            dragable=false;
            stat=c.def;
            }else{
            for(int i=0; i<3;i++){
            graphics2D.rotate(Math.PI / 2, height / 2.0, width / 2.0);
            ((ImageIcon)getIcon()).setImage(dest);
            width = toBuffered().getWidth();
            height = toBuffered().getHeight();
            }
            rotated=false;
            dragable=rotatable;
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
                ((ImageIcon)getIcon()).setImage(Card.back.getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT));
                viewable=false;
                repaint();
            }else{
                viewable=true;                
                ((ImageIcon)getIcon()).setImage(front);
            }
        }

        public void dealDirect() {
            atksavaiable--;
            if(atksavaiable<=0){
                dragable=false;
            }
            if(c.owner==p1){
                p2.Hp-=c.atk;
                if(p2.Hp<=0){
                    mw.win();
                }
            }else{
                p1.Hp-=c.atk;
                HpDisplay.setText(String.valueOf(p1.Hp));
                if(p1.Hp<=0){
                    mw.lose();
                }
            }
        }
    }

    public class Zone extends JLabel{
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

    public void sendToGrave(DraggableImage target) {
            remove(target);
            field.remove(target.c);
            images.remove(target);
            getZoneAt(target.start).empty();
            if(target.c.owner==p1){
                Graveyard1.contains.add(target.c);
            }else{
                Graveyard2.contains.add(target.c);
            }
    }

    public void reactivate(String string) {
        if(string.equals("Player1")){
            for(int i=1; i<6; i++){
                Card card=getDraggableImageAt(zones.get(i).getLocation()).c;
                if(card.ct.equals("Spell")){
                    if(((SpellTrap)card).activated){
                        card.activate(this);
                    }
                }
            }
            if(getDraggableImageAt(Field1.getLocation())!=null){
                getDraggableImageAt(Field1.getLocation()).c.activate(this);
            }
            if(getDraggableImageAt(Field2.getLocation())!=null){
                getDraggableImageAt(Field2.getLocation()).c.activate(this);
            }
        }
    }
}