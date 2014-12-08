package com.eidd.graphics;

/**
 * Created by geoffrey on 12/7/14.
 */
public class Segment {

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
}
