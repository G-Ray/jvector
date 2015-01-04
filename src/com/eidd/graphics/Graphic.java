package com.eidd.graphics;

import java.awt.*;

/**
 * Created by geoffrey on 1/4/15.
 */
public abstract class Graphic {

    protected Color color = Color.black;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract void draw(Graphics2D g2);
    public abstract void drawSelected(Graphics2D g2);
    public abstract boolean intersect(int x, int y);

}
