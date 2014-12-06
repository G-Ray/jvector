package com.eidd;

import com.eidd.view.UI;
import javax.swing.SwingUtilities;

public class Jvector {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UI view = new UI();
                view.pack();
                view.setVisible(true);
            }
        });
    }
}
