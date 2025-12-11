package main.ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MyMouseListener implements java.awt.event.MouseMotionListener{

    private int x, y = -10;

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
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
