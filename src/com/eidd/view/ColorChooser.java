package com.eidd.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by geoffrey on 1/8/15.
 */
public class ColorChooser extends JFrame {

    private static JColorChooser jColorChooser;

    public ColorChooser() {
        setTitle("Jvector - color selector");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setSize(new Dimension(650, 300));
        setResizable(false);

        jColorChooser = new JColorChooser();
        jColorChooser.remove(1);
        jColorChooser.setColor(Color.BLACK);

        add(jColorChooser);
    }

    public static Color getColor() {
        return jColorChooser.getColor();
    }

    public static JColorChooser getjColorChooser() {
        return jColorChooser;
    }
}
