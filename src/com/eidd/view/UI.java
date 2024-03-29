package com.eidd.view;

import com.eidd.Jvector;
import com.eidd.graphics.*;
import com.eidd.graphics.Point;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UI extends JFrame implements ChangeListener {

    /**
     * UI Buttons
     */
    private JButton pointBtn,
                    segmentBtn,
                    lineBtn,
                    triangleBtn,
                    circleBtn,
                    selectBtn,
                    saveBtn,
                    loadBtn,
                    fillBtn;

    /**
     * Contains all the graphics
     */
    private ArrayList<Graphic> graphics;

    /**
     * Contains selected graphics
     */
    private ArrayList<Graphic> selections;

    /**
     * Current mouse position
     */
    private JLabel mousePositionLabel;

    /**
     * The current graphic we are manipulating. If null, we are in select mode.
     */
    private Graphic curGraphic;

    /**
     * scale factor of the graphics
     */
    private double scaleFactor = 1;

    /**
     * Define if we are seleting multiple graphics(true) or only one(false)
     */
    private boolean multiSelect = false;

    /**
     * The canvas we are drawing on
     */
    private final Canvas canvas;

    /**
     * UI constructor
     */
    public UI() {
        // Initialize variables
        graphics = new ArrayList<Graphic>();
        selections = new ArrayList<Graphic>();
        curGraphic = new Point();
        mousePositionLabel = new JLabel();
        canvas = new Canvas();
        canvas.setBackground(Color.WHITE);

        // Keep track of color chooser
        ColorChooser.getjColorChooser().getSelectionModel().addChangeListener(this);

        // Set window properties
        setTitle("Jvector");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 800));
        setPreferredSize(new Dimension(1280, 800));

        // Define our buttons
        saveBtn = new JButton("Save");
        loadBtn = new JButton("Load");
        pointBtn = new JButton("Point");
        segmentBtn = new JButton("Segment");
        lineBtn = new JButton("Line");
        selectBtn = new JButton("Select");
        triangleBtn = new JButton("Triangle");
        circleBtn = new JButton("Circle");
        fillBtn = new JButton("Fill/Unfill");

        // Load icons
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/floppy-o.png"));
            saveBtn.setIcon(new ImageIcon(img));
            img = ImageIO.read(getClass().getResource("/icons/folder-open.png"));
            loadBtn.setIcon(new ImageIcon(img));
            img = ImageIO.read(getClass().getResource("/icons/circle.png"));
            pointBtn.setIcon(new ImageIcon(img));
            img = ImageIO.read(getClass().getResource("/icons/minus.png"));
            segmentBtn.setIcon(new ImageIcon(img));
            img = ImageIO.read(getClass().getResource("/icons/arrows-h.png"));
            lineBtn.setIcon(new ImageIcon(img));
            img = ImageIO.read(getClass().getResource("/icons/caret-up.png"));
            triangleBtn.setIcon(new ImageIcon(img));
            img = ImageIO.read(getClass().getResource("/icons/circle-thin.png"));
            circleBtn.setIcon(new ImageIcon(img));
            img = ImageIO.read(getClass().getResource("/icons/hand-o-up.png"));
            selectBtn.setIcon(new ImageIcon(img));
            img = ImageIO.read(getClass().getResource("/icons/paint-brush.png"));
            fillBtn.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Enable buttons
        pointBtn.setEnabled(true);
        segmentBtn.setEnabled(true);
        lineBtn.setEnabled(true);
        triangleBtn.setEnabled(true);
        circleBtn.setEnabled(true);
        selectBtn.setEnabled(true);
        saveBtn.setEnabled(true);
        loadBtn.setEnabled(true);
        fillBtn.setEnabled(true);

        // Set actionlisteners
        ActionListener pointListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curGraphic = new Point();
                selections.clear();
                repaint();
            }
        };

        ActionListener segmentListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curGraphic = new Segment();
                selections.clear();
                repaint();
            }
        };

        ActionListener lineListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curGraphic = new Line();
                selections.clear();
                repaint();
            }
        };

        ActionListener triangleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curGraphic = new Triangle();
                selections.clear();
                repaint();
            }
        };

        ActionListener circleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curGraphic = new Circle();
                selections.clear();
            }
        };

        ActionListener selectListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        // attach ActionListeners
        pointBtn.addActionListener(pointListener);
        segmentBtn.addActionListener(segmentListener);
        lineBtn.addActionListener(lineListener);
        triangleBtn.addActionListener(triangleListener);
        circleBtn.addActionListener(circleListener);
        selectBtn.addActionListener(selectListener);
        saveBtn.addActionListener(saveListener);
        loadBtn.addActionListener(loadListener);
        fillBtn.addActionListener(fillListener);

        // Define the layout
        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor=GridBagConstraints.NORTH;
        gbc.insets = new Insets(3,3,3,3);

        buttons.add(saveBtn, gbc);
        buttons.add(loadBtn, gbc);
        buttons.add(pointBtn, gbc);
        buttons.add(segmentBtn, gbc);
        buttons.add(lineBtn, gbc);
        buttons.add(triangleBtn, gbc);
        buttons.add(circleBtn, gbc);

        gbc.weighty = 1.0;
        buttons.add(selectBtn, gbc);
        buttons.add(fillBtn, gbc);

        mousePositionLabel.setText("0:0");
        buttons.add(mousePositionLabel, gbc);

        // Set a keyListener
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new keyDispatcher());

        add(buttons, BorderLayout.WEST);
        add(canvas);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        for(Graphic g : selections) {
            g.setColor(ColorChooser.getColor());
        }
        repaint();
    }

    /**
     * Custom keyEventDispatcher
     */
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
                if(e.getKeyCode() == KeyEvent.VK_X) {
                    double x=0;
                    double y=0;
                    if (selections.size()==2){
                        if (selections.get(0) instanceof Line && selections.get(1) instanceof Line){
                            x=Math.abs(((Line) selections.get(1)).getCst()-((Line) selections.get(0)).getCst())/Math.abs(((Line) selections.get(1)).getCoeff()-((Line) selections.get(0)).getCoeff());
                            y=((Line) selections.get(0)).getCoeff()*x+((Line) selections.get(0)).getCst();
                            Point p;
                            p = new Point((int) x, (int) y);
                            Segment s1= (Segment) selections.get(0);
                            Segment s2= (Segment) selections.get(1);
                            s1.setP1(p);
                            s2.setP1(p);
                            repaint();
                        }else if (selections.get(0) instanceof Segment && selections.get(1) instanceof Segment) {
                            x = Math.abs(((Segment) selections.get(1)).getCst() - ((Segment) selections.get(0)).getCst()) / Math.abs(((Segment) selections.get(1)).getCoeff() - ((Segment) selections.get(0)).getCoeff());
                            y = ((Segment) selections.get(0)).getCoeff() * x + ((Segment) selections.get(0)).getCst();
                            Point p;
                            p = new Point((int) x, (int) y);
                            if (((Segment) selections.get(0)).include(p)) {
                                graphics.add(new Point((int) x, (int) y));
                                repaint();
                            }
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_I) {
                    int m = 0;
                    int n = 0;
                    for (Graphic g : selections){
                        if(!(g instanceof Point)) return false; //there is not only points
                        m += ((Point) g).getX();
                        n += ((Point) g).getY();
                    }
                    graphics.add(new Point(m/selections.size(), n/selections.size()));
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

    /**
     * A canvas based on JPanel
     */
    private class Canvas extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {

        /**
         * Last mouse position, used to calculate new positions when dragging graphics
         */
        private java.awt.Point lastPos;

        /**
         * Canvas constructor
         */
        public Canvas() {
            addMouseMotionListener(this);
            addMouseListener(this);
            addMouseWheelListener(this);
        }

        /**
         * Paint method
         * @param g the graphics context to use for painting
         */
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

            if(curGraphic == null) { // Select mode
                for (Graphic g : selections) {
                    if(g instanceof Point && g.intersect(x, y)) {
                        ((Point) g).setLocation(x, y);
                    }
                    if(g instanceof Circle) {
                        if(((Circle) g).getP1().intersect(x, y)) {
                            ((Circle) g).setP1(new Point(x, y));
                            int x2 = ((Circle) g).getP2().getX() + (x - (int) lastPos.getX());
                            int y2 = ((Circle) g).getP2().getY() + (y - (int) lastPos.getY());
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
                        } else if(((Triangle) g).getP3().intersect(x, y)) {
                            ((Triangle) g).setP3(new Point(x, y));
                        }
                    }
                    lastPos = new java.awt.Point(x, y);
                }
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            // Update mouse location
            mousePositionLabel.setText(Integer.toString(mouseEvent.getX()) + ":" + Integer.toString(mouseEvent.getY()));

            if(curGraphic != null) {
                curGraphic.setColor(ColorChooser.getColor());

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
            if(SwingUtilities.isRightMouseButton(mouseEvent)) {
                curGraphic = null;
            }

            // select detection
            if(multiSelect) {
                boolean found = false;
                for (Graphic g : graphics) {
                    if(g.intersect(mouseEvent.getX(), mouseEvent.getY())) {
                        selections.add(g);
                        found = true;
                    }
                }
                if (!found) selections.clear();
            }

            if(!multiSelect) {
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

            if(!SwingUtilities.isLeftMouseButton(mouseEvent)) {
                return;
            }

            if(curGraphic != null) {
                curGraphic.setColor(ColorChooser.getColor());

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

        @Override
        public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
            int notches = mouseWheelEvent.getWheelRotation();
            if (notches < 0) {
                scaleFactor += 0.2;
                repaint();
            } else {
                if(scaleFactor > 1) {
                    scaleFactor -= 0.2;
                    repaint();
                }
            }
        }
    }

}
