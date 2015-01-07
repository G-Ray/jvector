package com.eidd.view;

import com.eidd.Jvector;
import com.eidd.graphics.*;
import com.eidd.graphics.Point;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UI extends JFrame implements ChangeListener {

    private JButton pointBtn,
                    segmentBtn,
                    lineBtn,
                    triangleBtn,
                    circleBtn,
                    selectBtn,
                    saveBtn,
                    loadBtn,
                    fillBtn;

    private JLabel mousePositionLabel;
    private ArrayList<Graphic> graphics;
    private ArrayList<Graphic> selections;
    private Graphic curGraphic;
    private JColorChooser jColorChooser;

    private double scaleFactor = 1;

    private boolean multiSelect = false;

    private final Canvas canvas;

    public UI() {
        graphics = new ArrayList<Graphic>();

        selections = new ArrayList<Graphic>();
        curGraphic = new Point();

        setTitle("Jvector");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1280, 800));
        setVisible(true);

        JMenuBar menu = new JMenuBar();
        canvas = new Canvas();

        saveBtn = new JButton("Save");
        loadBtn = new JButton("Load");
        pointBtn = new JButton("Point");
        segmentBtn = new JButton("Segment");
        lineBtn = new JButton("Line");
        selectBtn = new JButton("Select");
        triangleBtn = new JButton("Triangle");
        circleBtn = new JButton("Circle");
        fillBtn = new JButton("Fill/Unfill");
        jColorChooser = new JColorChooser();

        mousePositionLabel = new JLabel();

        pointBtn.setEnabled(true);
        segmentBtn.setEnabled(true);
        lineBtn.setEnabled(true);
        triangleBtn.setEnabled(true);
        circleBtn.setEnabled(true);
        selectBtn.setEnabled(true);
        saveBtn.setEnabled(true);
        loadBtn.setEnabled(true);
        fillBtn.setEnabled(true);

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

        ActionListener circleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("circle");
                curGraphic = new Circle();
                selections.clear();
            }
        };

        ActionListener selectListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                curGraphic = null; // Select mode
            }
        };

        ActionListener saveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Jvector.save(graphics);
            }
        };

        ActionListener loadListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphics = Jvector.load();
                repaint();
            }
        };

        ActionListener fillListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Graphic g : selections) {
                    if(g instanceof Triangle) {
                        ((Triangle) g).switchFillColor();
                    }
                    if(g instanceof Circle) {
                        ((Circle) g).switchFillColor();
                    }
                }
                repaint();
            }
        };

        pointBtn.addActionListener(pointListener);
        segmentBtn.addActionListener(segmentListener);
        lineBtn.addActionListener(lineListener);
        triangleBtn.addActionListener(triangleListener);
        circleBtn.addActionListener(circleListener);
        selectBtn.addActionListener(selectListener);
        saveBtn.addActionListener(saveListener);
        loadBtn.addActionListener(loadListener);
        fillBtn.addActionListener(fillListener);
        jColorChooser.getSelectionModel().addChangeListener(this);

        menu.add(saveBtn);
        menu.add(loadBtn);
        menu.add(pointBtn);
        menu.add(segmentBtn);
        menu.add(lineBtn);
        menu.add(triangleBtn);
        menu.add(circleBtn);
        menu.add(selectBtn);
        menu.add(fillBtn);
        mousePositionLabel.setMinimumSize(new Dimension(50, 20));
        mousePositionLabel.setPreferredSize(new Dimension(50, 20));
        mousePositionLabel.setText("0:0");
        menu.add(mousePositionLabel);

        jColorChooser.remove(1); // Remove color preview panel
        jColorChooser.setColor(Color.BLACK);

        menu.add(jColorChooser);
        setJMenuBar(menu);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new keyDispatcher());

        setContentPane(canvas);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        for(Graphic g : selections) {
            g.setColor(jColorChooser.getColor());
        }
        repaint();
    }

    private class keyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                    for (Graphic g : selections) {
                        graphics.remove(g);
                    }
                    selections.clear();
                    repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_ADD) {
                    scaleFactor += 0.2;
                    repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                    if(scaleFactor > 1) {
                        scaleFactor -= 0.2;
                        repaint();
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    multiSelect = true;
                }
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    multiSelect = false;
                }
            } else if (e.getID() == KeyEvent.KEY_TYPED) {

            }
            return false;
        }
    }

    private class Canvas extends JPanel implements MouseMotionListener, MouseListener {

        private java.awt.Point lastPos;

        public Canvas() {
            addMouseMotionListener(this);
            addMouseListener(this);
        }

        public void paint(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            double x = (getWidth() - scaleFactor * getWidth()) / 2;
            double y = (getHeight() - scaleFactor * getHeight()) / 2;
            g2.translate(x, y); // move to center of image
            g2.scale(scaleFactor, scaleFactor);

            if(curGraphic != null) curGraphic.drawPreview(g2);

            for(Graphic graphic : graphics) {
                graphic.draw(g2);
            }

            for(Graphic s : selections) {
                s.drawSelected(g2);
            }
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

            int x = mouseEvent.getX();
            int y = mouseEvent.getY();
            if(x<0) x=0;
            if(y<0) y=0;
            if(x>this.getWidth()) x=this.getWidth();
            if(y>this.getHeight()) y=this.getHeight();
            System.out.println(x);

            if(curGraphic == null) { // Select mode
                for (Graphic g : selections) {
                    if(g instanceof Point) {
                        ((Point) g).setLocation(x, y);
                        repaint();
                    }
                    if(g instanceof Circle) {
                        if(((Circle) g).getP1().intersect(x, y)) {
                            ((Circle) g).setP1(new Point(x, y));
                            System.out.println(mouseEvent.getX() - (int) lastPos.getX());
                            int x2 = ((Circle) g).getP2().getX() + (x - (int) lastPos.getX());
                            int y2 = ((Circle) g).getP2().getY() + (y - (int) lastPos.getY());

                            lastPos = new java.awt.Point(x, y);
                            ((Circle) g).setP2(new Point(x2, y2));
                        }
                        if(((Circle) g).getP2().intersect(x, y)) {
                            ((Circle) g).setP2(new Point(x, y));
                        }
                    }
                    if(g instanceof Segment) {
                        if(((Segment) g).getP1().intersect(x, y)) {
                            ((Segment) g).setP1(new Point(x, y));
                        }
                        else if(((Segment) g).getP2().intersect(x, y)) {
                            ((Segment) g).setP2(new Point(x, y));
                        }
                    }
                    if(g instanceof Triangle) {
                        if(((Triangle) g).getP1().intersect(x, y)) {
                            ((Triangle) g).setP1(new Point(x, y));
                        }
                        else if(((Triangle) g).getP2().intersect(x, y)) {
                            ((Triangle) g).setP2(new Point(x, y));
                        }
                        else if(((Triangle) g).getP3().intersect(x, y)) {
                            ((Triangle) g).setP3(new Point(x, y));
                        }
                    }
                }
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            // Update mouse location
            mousePositionLabel.setText(Integer.toString(mouseEvent.getX()) + ":" + Integer.toString(mouseEvent.getY()));

            if(curGraphic != null) {
                curGraphic.setColor(jColorChooser.getColor());

                // Point
                if (curGraphic instanceof Point) ((Point) curGraphic).setLocation(mouseEvent.getX(), mouseEvent.getY());

                // Segment and line
                if (curGraphic instanceof Segment && ((Segment) curGraphic).getX1() >= 0) { // First point is already placed
                    ((Segment) curGraphic).setLocation(((Segment) curGraphic).getX1(), ((Segment) curGraphic).getY1(), mouseEvent.getX(), mouseEvent.getY());
                }

                if(curGraphic instanceof Circle && ((Circle) curGraphic).getP1().getX() >= 0) {
                    ((Circle) curGraphic).setP2(new Point(mouseEvent.getX(), mouseEvent.getY()));
                }
            }
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            // select detection
            if(curGraphic == null && multiSelect) {
                boolean found = false;
                for (Graphic g : graphics) {
                    if(g.intersect(mouseEvent.getX(), mouseEvent.getY())) {
                        selections.add(g);
                        found = true;
                    }
                }
                if (!found) selections.clear();
            }

            if(curGraphic == null && !multiSelect) {
                boolean found = false;
                for (Graphic g : graphics) {
                    if(g.intersect(mouseEvent.getX(), mouseEvent.getY())) {
                        if(!selections.isEmpty()) selections.clear();
                        selections.add(g);
                        found = true;
                    }
                }
                if(!found) selections.clear();
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            lastPos = mouseEvent.getPoint();

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

                if (curGraphic instanceof Circle && ((Circle) curGraphic).getP1().getX() < 0) { // Set the first point of circle
                    ((Circle) curGraphic).setP1(new Point(mouseEvent.getX(), mouseEvent.getY()));
                } else if (curGraphic instanceof Circle && !((Circle) curGraphic).getP2().isSet()) { // Set the second point
                    ((Circle) curGraphic).setP2(new Point(mouseEvent.getX(), mouseEvent.getY(), true));
                    graphics.add(curGraphic);
                }
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

            if(curGraphic instanceof Circle && ((Circle) curGraphic).getP2().getX() > 0) {
                curGraphic = new Circle();
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
