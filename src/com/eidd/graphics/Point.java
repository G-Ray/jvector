package com.eidd.graphics;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by geoffrey on 12/6/14.
 */
public class Point extends Graphic {

    public final static int width = 10,
                            height = 10;

    private int x,
                y;

    public Point() {
        this.setLocation(0, 0);
    }

    public Point(int x, int y) {
        this.setLocation(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return (this.x + ":" + this.y);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(this.getColor());
        g2.drawOval(this.getX() - Point.width / 2, this.getY() - Point.height / 2, Point.width, Point.height);
        g2.fillOval(this.getX() - Point.width / 2, this.getY() - Point.height / 2, Point.width, Point.height);
    }

    public void drawSelected(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.drawOval(this.getX() - Point.width / 2, this.getY() - Point.height / 2, Point.width, Point.height);
    }

    public boolean intersect(int x, int y) {
        Line2D l = new Line2D.Double(this.x, this.y, this.x, this.y);
        return (l.ptSegDist(x, y) < 5);
    }
}
