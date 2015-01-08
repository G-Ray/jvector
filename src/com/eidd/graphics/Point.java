package com.eidd.graphics;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by geoffrey on 12/6/14.
 */
public class Point extends Graphic {

    /**
     * Point properties
     */
    public final static int width = 10,
                            height = 10;

    /**
     * Point coordinates
     */
    private int x,
                y;

    /**
     * Define if the point is set(true) or not(false)
     */
    private boolean set = false;

    /**
     * Default constructor
     */
    public Point() {
        this.setLocation(0, 0);
    }

    /**
     * Constructor
     * @param x the coordinate along x-axis
     * @param y the coordinate along y-axis
     */
    public Point(int x, int y) {
        this.setLocation(x, y);
    }

    /**
     * Constructor
     * @param x the coordinate along x-axis
     * @param y the coordinate along y-axis
     * @param set define the point as set(true) or not(false)
     */
    public Point(int x, int y, boolean set) {
        this.setLocation(x, y);
        this.set = set;
    }

    /**
     * Get x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Get y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Set point location
     * @param x coordinate along x-axis
     * @param y coordinate along y-axis
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Stringify the point
     * @return string representation of the point
     */
    public String toString() {
        return (this.x + ":" + this.y);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(this.getColor());
        g2.drawOval(this.getX() - Point.width / 2, this.getY() - Point.height / 2, Point.width, Point.height);
        g2.fillOval(this.getX() - Point.width / 2, this.getY() - Point.height / 2, Point.width, Point.height);
    }

    @Override
    public void drawSelected(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.drawOval(this.getX() - Point.width / 2, this.getY() - Point.height / 2, Point.width, Point.height);
    }

    @Override
    public boolean intersect(int x, int y) {
        Line2D l = new Line2D.Double(this.x, this.y, this.x, this.y);
        return (l.ptSegDist(x, y) < 9);
    }

    @Override
    public void drawPreview(Graphics2D g2) {
        this.drawSelected(g2);
    }

    /**
     *
     * @return true if set, false else
     */
    public boolean isSet() {
        return set;
    }
}
