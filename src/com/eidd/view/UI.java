package com.eidd.view;

import com.eidd.graphics.*;
import com.eidd.graphics.Point;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UI extends JFrame implements ChangeListener {

    private JButton pointBtn,
                    segmentBtn,
                    lineBtn,
                    triangleBtn,
                    selectBtn;

    private JLabel mousePositionLabel;
    private ArrayList<Graphic> graphics;
    private ArrayList<Graphic> selections;
    private Graphic curGraphic;
    private JColorChooser jColorChooser;

    public UI() {
        graphics = new ArrayList<Graphic>();
        selections = new ArrayList<Graphic>();
        curGraphic = new Point();

        setTitle("Jvector");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1280, 800));
        setVisible(true);

        JMenuBar menu = new JMenuBar();
        final Canvas canvas = new Canvas();

        pointBtn = new JButton("Point");
        segmentBtn = new JButton("Segment");
        lineBtn = new JButton("Line");
        selectBtn = new JButton("Select");
        triangleBtn = new JButton("Triangle");
        jColorChooser = new JColorChooser();

        mousePositionLabel = new JLabel();

        pointBtn.setEnabled(true);
        segmentBtn.setEnabled(true);
        lineBtn.setEnabled(true);
        selectBtn.setEnabled(true);

        ActionListener pointListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                curGraphic = new Point();
                selections.clear();
                repaint();
            }
        };

        ActionListener segmentListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                curGraphic = new Segment();
                selections.clear();
                repaint();
            }
        };

        ActionListener lineListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                curGraphic = new Line();
                selections.clear();
                repaint();
            }
        };

        ActionListener triangleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("triangle");
                curGraphic = new Triangle();
                selections.clear();
                repaint();
            }
        };

        ActionListener selectListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                curGraphic = null;
            }
        };

        ActionListener colorListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("color");
            }
        };

        pointBtn.addActionListener(pointListener);
        segmentBtn.addActionListener(segmentListener);
        lineBtn.addActionListener(lineListener);
        triangleBtn.addActionListener(triangleListener);
        selectBtn.addActionListener(selectListener);
        jColorChooser.getSelectionModel().addChangeListener(this);

        menu.add(pointBtn);
        menu.add(segmentBtn);
        menu.add(lineBtn);
        menu.add(triangleBtn);
        menu.add(selectBtn);
        mousePositionLabel.setMinimumSize(new Dimension(50, 20));
        mousePositionLabel.setPreferredSize(new Dimension(50, 20));
        mousePositionLabel.setText("0:0");
        menu.add(mousePositionLabel);

        jColorChooser.remove(1);
        jColorChooser.setColor(Color.BLACK);

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

    @Override
    public void stateChanged(ChangeEvent e) {
        for(Graphic g : selections) {
            g.setColor(jColorChooser.getColor());
        }
        repaint();
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

            if(curGraphic != null) curGraphic.drawSelected(g2);

            for(Graphic graphic : graphics) {
                graphic.draw(g2);
            }

            for(Graphic s : selections) {
                s.drawSelected(g2);
            }
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            if(curGraphic != null) {
                curGraphic.setColor(jColorChooser.getColor());

                // Update mouse location
                mousePositionLabel.setText(Integer.toString(mouseEvent.getX()) + ":" + Integer.toString(mouseEvent.getY()));

                // Point
                if (curGraphic instanceof Point) ((Point) curGraphic).setLocation(mouseEvent.getX(), mouseEvent.getY());

                // Segment and line
                if (curGraphic instanceof Segment && ((Segment) curGraphic).getX1() >= 0) { // First point is already placed
                    ((Segment) curGraphic).setLocation(((Segment) curGraphic).getX1(), ((Segment) curGraphic).getY1(), mouseEvent.getX(), mouseEvent.getY());
                }

            }
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if(curGraphic != null) {
                curGraphic.setColor(jColorChooser.getColor());

                if (curGraphic instanceof Point) {
                    graphics.add(curGraphic);
                }

                if (curGraphic instanceof Segment && ((Segment) curGraphic).getX1() < 0) { // Set the first point of segment/line
                    ((Segment) curGraphic).setLocation(mouseEvent.getX(), mouseEvent.getY(), ((Segment) curGraphic).getX2(), ((Segment) curGraphic).getY2());
                } else if (curGraphic instanceof Segment && ((Segment) curGraphic).getX1() >= 0) { // Set the second point
                    ((Segment) curGraphic).setLocation(((Segment) curGraphic).getX1(), ((Segment) curGraphic).getY1(), mouseEvent.getX(), mouseEvent.getY());
                    // Segment or Line is built
                    graphics.add(curGraphic);
                }

                if(curGraphic instanceof Triangle && ((Triangle) curGraphic).getP1().getX() < 0) { // Set the first point of a triangle
                    ((Triangle) curGraphic).setP1(new Point(mouseEvent.getX(), mouseEvent.getY()));
                }
                else if(curGraphic instanceof Triangle && ((Triangle) curGraphic).getP2().getX() < 0) { // Set the second point
                    ((Triangle) curGraphic).setP2(new Point(mouseEvent.getX(), mouseEvent.getY()));
                }
                else if(curGraphic instanceof Triangle && ((Triangle) curGraphic).getP3().getX() < 0) { // Set the third point
                    ((Triangle) curGraphic).setP3(new Point(mouseEvent.getX(), mouseEvent.getY()));
                    graphics.add(curGraphic);
                }
            }

            // select detection
            if(curGraphic == null) {
                Point2D p = new Point2D.Double(mouseEvent.getX(), mouseEvent.getY());
                boolean found = false;
                for (Graphic g : graphics) {
                    if(g.intersect(mouseEvent.getX(), mouseEvent.getY())) {
                        System.out.println("TOUCHE!!!");
                        selections.add(g);
                        found = true;
                    }
                }
                if (!found) selections.clear();
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

            if(curGraphic instanceof  Triangle && ((Triangle) curGraphic).getP3().getX() > 0) {
                curGraphic = new Triangle();
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
