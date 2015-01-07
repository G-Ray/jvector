package com.eidd.graphics;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by geoffrey on 1/5/15.
 */
public class Triangle extends Graphic {

    private Point p1,
                  p2,
                  p3;

    private boolean fillColor;

    public Triangle() {
        this(new Point(-1,-1), new Point(-1,-1), new Point(-1,-1));
    }

    public Triangle(Point p1, Point p2, Point p3) {
        this.setLocation(p1, p2, p3);
    }

    public void setLocation(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Point getP1() {
        return p1;
    }
    public Point getP2() {
        return p2;
    }
    public Point getP3() {
        return p3;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }
    public void setP2(Point p2) {
        this.p2 = p2;
    }
    public void setP3(Point p3) {
        this.p3 = p3;
    }

    public void draw(Graphics2D g2) {
        if(this.p1.getX()<0 || this.p2.getX()<0 || this.p3.getX()<0) return; // Triangle is not set, do not draw it

        g2.setColor(this.getColor());

        int [] x = {p1.getX(), p2.getX(), p3.getX()};
        int [] y = {p1.getY(), p2.getY(), p3.getY()};
        Polygon p = new Polygon(x, y, 3);
        g2.drawPolygon(p);
        if(fillColor)
            g2.fillPolygon(p);
    }

    public void switchFillColor() {
        if(!fillColor)
            this.fillColor = true;
        else this.fillColor = false;
    }

    public void drawSelected(Graphics2D g2) {
        if(p1.getX()>=0)
            p1.draw(g2);
        if(p2.getX()>=0)
            p2.draw(g2);
        if(p3.getX()>=0)
            p3.draw(g2);
    }

    public boolean intersect(int x, int y) {
        Line2D l1 = new Line2D.Double(this.p1.getX(), this.p1.getY(), this.p2.getX(), this.p2.getY());
        Line2D l2 = new Line2D.Double(this.p1.getX(), this.p1.getY(), this.p3.getX(), this.p3.getY());
        Line2D l3 = new Line2D.Double(this.p2.getX(), this.p2.getY(), this.p3.getX(), this.p3.getY());
        return ((l1.ptSegDist(x, y) < 5) || (l2.ptSegDist(x, y) < 5) || (l3.ptSegDist(x, y) < 5));
    }

    @Override
    public void drawPreview(Graphics2D g2) {
        if(p1.getX()>=0) {
            p1.draw(g2);
        }
        if(p2.getX()>=0) {
            p2.draw(g2);
            Line2D.Double l = new Line2D.Double(this.p1.getX(), this.p1.getY(), this.p2.getX(), this.p2.getY());
            g2.draw(l);
        }
        if(p3.getX()>=0) {
            this.draw(g2);
        }
    }
}
