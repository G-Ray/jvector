package com.eidd.view;

import com.eidd.graphics.Graphic;
import com.eidd.graphics.Line;
import com.eidd.graphics.Point;
import com.eidd.graphics.Segment;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public class UI extends JFrame {

    private JButton pointBtn,
                    segmentBtn,
                    lineBtn;

    private JLabel mousePositionLabel;
    private ArrayList<Graphic> graphics;
    private Graphic curGraphic;
    private JColorChooser jColorChooser;

    public UI() {
        graphics = new ArrayList<Graphic>();

        setTitle("Jvector");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 800));
        setVisible(true);

        JMenuBar menu = new JMenuBar();
        final Canvas canvas = new Canvas();

        pointBtn = new JButton("Point");
        segmentBtn = new JButton("Segment");
        lineBtn = new JButton("Line");
        jColorChooser = new JColorChooser();

        mousePositionLabel = new JLabel();

        pointBtn.setEnabled(true);
        segmentBtn.setEnabled(true);
        lineBtn.setEnabled(true);

        ActionListener pointListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                curGraphic = new Point();
            }
        };

        ActionListener segmentListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                curGraphic = new Segment();
            }
        };

        ActionListener lineListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                curGraphic = new Line();
            }
        };

        pointBtn.addActionListener(pointListener);
        segmentBtn.addActionListener(segmentListener);
        lineBtn.addActionListener(lineListener);

        menu.add(pointBtn);
        menu.add(segmentBtn);
        menu.add(lineBtn);
        mousePositionLabel.setMinimumSize(new Dimension(50, 20));
        mousePositionLabel.setText("0:0");
        menu.add(mousePositionLabel);

        jColorChooser.remove(1);

        AbstractColorChooserPanel[] panels = jColorChooser.getChooserPanels();
        for (AbstractColorChooserPanel accp : panels) {
            if (!accp.getDisplayName().equals("RGB") && !accp.getDisplayName().equals("Swatches")) {
                jColorChooser.removeChooserPanel(accp);
            }
        }

        menu.add(jColorChooser);
        setJMenuBar(menu);

        setContentPane(canvas);
    }

    private class Canvas extends JPanel implements MouseMotionListener, MouseListener {

        public Canvas() {
            addMouseMotionListener(this);
            addMouseListener(this);
        }

        public void paint(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if(curGraphic != null) curGraphic.draw(g2);

            for(Graphic graphic : graphics) {
                graphic.draw(g2);
            }
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            mousePositionLabel.setText(Integer.toString(mouseEvent.getX()) + ":" + Integer.toString(mouseEvent.getY()));
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            // Update mouse location
            mousePositionLabel.setText(Integer.toString(mouseEvent.getX()) + ":" + Integer.toString(mouseEvent.getY()));

            // Point
            if(curGraphic instanceof Point) ((Point) curGraphic).setLocation(mouseEvent.getX(), mouseEvent.getY());

            // Segment and line
            if(curGraphic instanceof Segment && ((Segment) curGraphic).getX1()>=0) { // First point is already placed
                ((Segment) curGraphic).setLocation(((Segment) curGraphic).getX1(), ((Segment) curGraphic).getY1(), mouseEvent.getX(), mouseEvent.getY());
            }
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            curGraphic.setColor(jColorChooser.getColor());

            if(curGraphic instanceof Point) {
                graphics.add(curGraphic);
            }

            if(curGraphic instanceof Segment && ((Segment)curGraphic).getX1()<0) { // Set the first point of segment/line
                ((Segment)curGraphic).setLocation(mouseEvent.getX(), mouseEvent.getY(), ((Segment) curGraphic).getX2(), ((Segment) curGraphic).getY2());
            }
            else if(curGraphic instanceof Segment && ((Segment)curGraphic).getX1()>=0) { // Set the second point
                ((Segment) curGraphic).setLocation(((Segment) curGraphic).getX1(), ((Segment) curGraphic).getY1(), mouseEvent.getX(), mouseEvent.getY());

                // Segment or Line is built
                graphics.add(curGraphic);
            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            if(curGraphic instanceof Point) curGraphic = new Point();

            if(curGraphic instanceof Segment && ((Segment) curGraphic).isPlaced()) {
                if(curGraphic instanceof Line)
                    curGraphic = new Line();
                else if(curGraphic instanceof Segment)
                    curGraphic = new Segment();
            }
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

}
