package com.eidd.graphics;

import java.awt.*;

/**
 * Created by geoffrey on 1/5/15.
 */
public class Circle extends Graphic {

    /**
     * Points defining the circle
     */
    private Point p1, // center
                  p2; // a point on the circle

    /**
     * the radius of the circle
     */
    private int radius;

    /**
     * Define if the circle is filled
     */
    private boolean fillColor;

    /**
     * Default constructor
     */
    public Circle() {
        this(new Point(-1,-1), new Point(-1,-1));
    }

    /**
     * Construct a circle with two points
     * @param p1 the first point (center)
     * @param p2 the second point (a point on the circle)
     */
    public Circle(Point p1, Point p2) {
        this.setLocation(p1, p2);
        this.radius = 0;
    }

    /**
     * Set the circle location
     * @param p1 first point (center)
     * @param p2 second point
     */
    public void setLocation(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Get the
     * @return p1 the first point (center)
     */
    public Point getP1() {
        return p1;
    }
    /**
     * Get the
     * @return p1 the second point
     */
    public Point getP2() {
        return p2;
    }

    /**
     * set the first point
     * @param p1 the first point(center)
     */
    public void setP1(Point p1) {
        this.p1 = p1;
    }
    /**
     * set the second point
     * @param p2 the second point
     */
    public void setP2(Point p2) {
        this.p2 = p2;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(this.p1.getX()<0 || this.p2.getX()<0) return; // Triangle is not set, do not draw it

        g2.setColor(this.getColor());
        double d = Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
        this.radius = (int) d;
        int x = p1.getX() - radius;
        int y = p1.getY() - radius;
        g2.drawOval(x, y, radius *2, radius *2);
        if(fillColor)
            g2.fillOval(p1.getX() - radius, p1.getY()-radius, radius*2, radius*2);
    }

    /**
     * Switch on or off fillColor
     */
    public void switchFillColor() {
        if(!fillColor)
            this.fillColor = true;
        else this.fillColor = false;
    }

    @Override
    public void drawSelected(Graphics2D g2) {
        if(p1.getX()>=0)
            p1.draw(g2);
        if(p2.getX()>=0)
            p2.draw(g2);
    }

    @Override
    public boolean intersect(int x, int y) {
        int newRadius = (int) Math.sqrt((p1.getX() - x) * (p1.getX() - x) + (p1.getY() - y) * (p1.getY() - y));
        return (Math.abs(radius - newRadius) < 5);
    }

    @Override
    public void drawPreview(Graphics2D g2) {
        if(p1.getX()>=0)
            p1.draw(g2);
        if(p2.getX()>=0 && !p2.isSet())
            this.draw(g2);
    }
}
