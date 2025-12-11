package main.ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MyMouseListener implements java.awt.event.MouseMotionListener{
    
    private int size = 10;
    private int x, y = -size;

    @Override
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e){}

    public void draw(Graphics2D g2){
        g2.setColor(Color.BLUE);
        g2.fillRect(x - size/2, y -size/2, size, size);
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
}
