package com.eidd.graphics;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by geoffrey on 12/7/14.
 */
public class Segment extends Graphic {

    public final static int width = 20,
                            height = 20;

    private Point p1,
                  p2;

    public Segment() {
        this.setLocation(-1, -1, -1, -1);
    }

    public Segment(int x1, int y1, int x2, int y2) {
        this.setLocation(x1, y1, x2, y2);
    }

    public int getX1() {
        return p1.getX();
    }

    public int getY1() {
        return p1.getY();
    }

    public int getX2() {
        return p2.getX();
    }

    public int getY2() {
        return p2.getY();
    }

    public void setLocation(int x1, int y1, int x2, int y2) {
        p1 = new Point(x1, y1);
        p2 = new Point(x2, y2);
    }

    public String toString() {
        return (p1.getX() + ":" + p1.getY() + "-" + p2.getX() + ":" + p2.getY());
    }

    public void draw(Graphics2D g2) {
        if(this.getX2()<0) return;

        g2.setColor(this.getColor());
        Line2D l;

        l = new Line2D.Double(this.getX1(), this.getY1(), this.getX2(), this.getY2());
        g2.draw(l);
    }

    public boolean isPlaced() {
        return (this.getX1()>=0 && this.getX2()>=0);
    }
}
