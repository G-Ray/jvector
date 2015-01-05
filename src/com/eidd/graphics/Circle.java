package com.eidd.graphics;

import java.awt.*;

/**
 * Created by geoffrey on 1/5/15.
 */
public class Circle extends Graphic {

    private Point p1, // center
                  p2; // point on the circle

    private int radius; //radius

    public Circle() {
        this(new Point(-1,-1), new Point(-1,-1));
    }

    public Circle(Point p1, Point p2) {
        this.setLocation(p1, p2);
        this.radius = 0;
    }

    public void setLocation(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }
    public Point getP2() {
        return p2;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }
    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public void draw(Graphics2D g2) {
        if(this.p1.getX()<0 || this.p2.getX()<0) return; // Triangle is not set, do not draw it

        g2.setColor(this.getColor());
        double d = Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
        this.radius = (int) d;
        int x = p1.getX() - radius;
        int y = p1.getY() - radius;
        g2.drawOval(x, y, radius *2, radius *2);
    }

    public void drawSelected(Graphics2D g2) {
        if(p1.getX()>=0)
            p1.draw(g2);
        if(p2.getX()>=0)
            p2.draw(g2);
    }

    public boolean intersect(int x, int y) {
        int newRadius = (int) Math.sqrt((p1.getX() - x) * (p1.getX() - x) + (p1.getY() - y) * (p1.getY() - y));
        return (Math.abs(radius - newRadius) < 5);
    }
}
