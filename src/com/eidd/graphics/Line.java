package com.eidd.graphics;

/**
 * Created by geoffrey on 12/8/14.
 */
public class Line extends Segment {

    public double getCoeff() {
        double y = this.getY2() - this.getY1();
        double x = this.getX2() - this.getX1();
        return y/x;
    }

}
