package com.eidd.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class UI extends JFrame {

    private JButton pointBtn,
                    lineBtn;

    private JLabel mousePositionLabel;

    public UI() {
        setTitle("Jvector");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JMenuBar menu = new JMenuBar();

        pointBtn = new JButton("Point");
        lineBtn = new JButton("Line");

        mousePositionLabel = new JLabel();

        pointBtn.setEnabled(true);
        lineBtn.setEnabled(false);

        ActionListener pointListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
            }
        };

        ActionListener lineListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
            }
        };

        pointBtn.addActionListener(pointListener);
        lineBtn.addActionListener(lineListener);

        menu.add(pointBtn);
        menu.add(lineBtn);
        menu.add(mousePositionLabel);
        setJMenuBar(menu);

        Canvas canvas = new Canvas();
        setContentPane(canvas);
    }

    private class Canvas extends JPanel implements MouseMotionListener {

        public Canvas() {
            addMouseMotionListener(this);
        }

        @Override
        public void mouseDragged(MouseEvent arg0) {

        }

        @Override
        public void mouseMoved(MouseEvent arg0) {
            mousePositionLabel.setText(Integer.toString(arg0.getX()) + ":" + Integer.toString(arg0.getY()));
        }

        public void paint(Graphics g) {
            System.out.println("PAINT");
            Graphics2D g2 = (Graphics2D) g;
            g2.drawOval(50, 50, 5, 5);
            g2.fillOval(50, 50, 5, 5);
        }
    }

}
