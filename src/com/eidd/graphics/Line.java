package com.eidd.graphics;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by geoffrey on 12/8/14.
 */
public class Line extends Segment {

    public double getCoeff() {
        double y = this.getY2() - this.getY1();
        double x = this.getX2() - this.getX1();
        return y/x;
    }

    public void draw(Graphics2D g2) {
        if(this.getX2()<0) return;

        g2.setColor(this.getColor());
        Line2D line;
        double coeff = this.getCoeff();

        double y0 = (this.getY1() - (coeff * this.getX1()));
        double x0 = 0.;
        double xMax = 1920; //TODO: avoid this hardcoded const
        double yMax = coeff * xMax + y0;
        if(this.getX1() < this.getX2())
            line = new Line2D.Double(x0, y0, xMax, yMax);
        else line = new Line2D.Double(xMax, yMax, x0, y0);
        g2.draw(line);
    }

    public void drawPreview(Graphics2D g2) {
        if(this.getX1()<0) return;
        super.p1.draw(g2);
        if(this.getX2()<0) return;
        this.draw(g2);
    }

    public boolean intersect(int x, int y) {
        Line2D l = new Line2D.Double(this.getX1(), this.getY1(), this.getX2(), this.getY2());
        return (l.ptLineDist(x, y) < 5);
    }
}
