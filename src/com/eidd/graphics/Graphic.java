package com.eidd.graphics;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by geoffrey on 1/4/15.
 */
public abstract class Graphic implements Serializable {

    /**
     * Graphic properties
     */
    protected Color color = Color.black;

    /**
     * Set the color
     * @param color the color to apply
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the color
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Draw the point
     * @param g2 the graphic context to use
     */
    public abstract void draw(Graphics2D g2);

    /**
     * Draw the point when selected
     * @param g2 the graphic context to use
     */
    public abstract void drawSelected(Graphics2D g2);

    /**
     * Find if the point intersects with coordinates
     * @param x x
     * @param y y
     * @return true if the graphic intersects with x,y, false else
     */
    public abstract boolean intersect(int x, int y);

    /**
     * Draw of preview of the graphic being constructed
     * @param g2 the graphic context to use
     */
    public abstract void drawPreview(Graphics2D g2);

}