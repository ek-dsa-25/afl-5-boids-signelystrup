package main.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class MyMouseListener implements java.awt.event.MouseMotionListener{
    
    private int size = 50;
    private int x, y = -size;
    private Rectangle2D hitBox = new Rectangle(x ,y , size, size);

    @Override
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e){
                x = e.getX();
        y = e.getY();
    }

    public void draw(Graphics2D g2){
       // g2.setColor(Color.BLUE);
        //g2.fillRect(x , y, size, size);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSize(){
        return size;
    }

    public Rectangle2D getHitBox(){
        return hitBox;
    }
}
