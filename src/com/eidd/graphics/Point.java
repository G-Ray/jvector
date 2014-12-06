package com.eidd.graphics;

/**
 * Created by geoffrey on 12/6/14.
 */
public class Point {

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
}