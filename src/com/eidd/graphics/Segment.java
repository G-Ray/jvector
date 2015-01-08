package com.eidd.graphics;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by geoffrey on 12/7/14.
 */
public class Segment extends Graphic {

    /**
     * Points defining the segment
     */
    protected Point p1;
    protected Point p2;

    /**
     * Default constructor
     */
    public Segment() {
        this.setLocation(-1, -1, -1, -1);
    }

    /**
     * Construct a Segment with x1, y1, x2, y2
     * @param x1 first point coordinate along x-axis
     * @param y1 first point coordinate along y-axis
     * @param x2 second point coordinate along x-axis
     * @param y2 second point coordinate along y-axis
     */
    public Segment(int x1, int y1, int x2, int y2) {
        this.setLocation(x1, y1, x2, y2);
    }

    /**
     * Get x1
     * @return x1
     */
    public int getX1() {
        return p1.getX();
    }
    /**
     * Get y1
     * @return y1
     */
    public int getY1() {
        return p1.getY();
    }

    /**
     * Get x2
     * @return x2
     */
    public int getX2() {
        return p2.getX();
    }
    /**
     * Get y2
     * @return y2
     */
    public int getY2() {
        return p2.getY();
    }

    /**
     * Get the first point
     * @return the first point
     */
    public Point getP1() {
        return p1;
    }
    /**
     * Get the second point
     * @return the second point
     */
    public Point getP2() {
        return p2;
    }

    /**
     * Set the first point
     * @param p1 the new point to set
     */
    public void setP1(Point p1) {
        this.p1 = p1;
    }
    /**
     * Set the second point
     * @param p2 the new point to set
     */
    public void setP2(Point p2) {
        this.p2 = p2;
    }

    /**
     * Set the segment location
     * @param x1 x1
     * @param y1 y1
     * @param x2 x2
     * @param y2 y2
     */
    public void setLocation(int x1, int y1, int x2, int y2) {
        p1 = new Point(x1, y1);
        p2 = new Point(x2, y2);
    }

    /**
     * Stringify the segment
     * @return the string representation of the segment
     */
    public String toString() {
        return (p1.getX() + ":" + p1.getY() + "-" + p2.getX() + ":" + p2.getY());
    }

    @Override
    public void draw(Graphics2D g2) {
        if(this.getX2()<0) return;

        g2.setColor(this.getColor());
        Line2D l;

        l = new Line2D.Double(this.getX1(), this.getY1(), this.getX2(), this.getY2());
        g2.draw(l);
    }

    @Override
    public void drawSelected(Graphics2D g2) {
        if(this.getX2()<0) return;

        p1.draw(g2);
        p2.draw(g2);
    }

    @Override
    public void drawPreview(Graphics2D g2) {
        if(this.getX1()<0) return;
        p1.draw(g2);
        if(this.getX2()<0) return;
        p2.draw(g2);
        Line2D.Double l = new Line2D.Double(this.getX1(), this.getY1(), this.getX2(), this.getY2());
        g2.draw(l);
    }

    /**
     *
     * @return true if placed, false else
     */
    public boolean isPlaced() {
        return (this.getX1()>=0 && this.getX2()>=0);
    }

    @Override
    public boolean intersect(int x, int y) {
        Line2D l = new Line2D.Double(this.getX1(), this.getY1(), this.getX2(), this.getY2());
        return (l.ptSegDist(x, y) < 5);
    }
}