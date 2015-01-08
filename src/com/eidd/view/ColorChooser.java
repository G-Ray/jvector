package com.eidd.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by geoffrey on 1/8/15.
 */
public class ColorChooser extends JFrame {

    /**
     * jColorChooser component
     */
    private static JColorChooser jColorChooser;

    /**
     * ColorChooser constructor
     */
    public ColorChooser() {
        // Define window properties
        setTitle("Jvector - color selector");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(new Dimension(650, 300));
        setResizable(false);

        // Build our JColorChooser
        jColorChooser = new JColorChooser();
        jColorChooser.remove(1);
        jColorChooser.setColor(Color.BLACK);

        add(jColorChooser);
    }

    /**
     * Get the selected color
     * @return the current color
     */
    public static Color getColor() {
        return jColorChooser.getColor();
    }

    /**
     * Get the jColorChooser
     * @return jColorChooser
     */
    public static JColorChooser getjColorChooser() {
        return jColorChooser;
    }
}
