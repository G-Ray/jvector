package com.eidd.view;

import com.eidd.graphics.Line;
import com.eidd.graphics.Point;
import com.eidd.graphics.Segment;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class UI extends JFrame {

    private JButton pointBtn,
                    segmentBtn,
                    lineBtn;

    private JLabel mousePositionLabel;
    private ArrayList<Point> points;
    private ArrayList<Segment> segments;
    private ArrayList<Line> lines;
    private Point movingPoint;
    private Segment movingSegment;

    public UI() {
        points = new ArrayList<Point>();
        segments = new ArrayList<Segment>();
        lines = new ArrayList<Line>();

        setTitle("Jvector");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 800));
        setVisible(true);

        JMenuBar menu = new JMenuBar();
        final Canvas canvas = new Canvas();

        pointBtn = new JButton("Point");
        segmentBtn = new JButton("Segment");
        lineBtn = new JButton("Line");

        mousePositionLabel = new JLabel();

        pointBtn.setEnabled(true);
        segmentBtn.setEnabled(true);
        lineBtn.setEnabled(true);

        ActionListener pointListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                movingSegment = null;
                movingPoint = new Point();
            }
        };

        ActionListener segmentListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                movingPoint = null;
                movingSegment = new Segment();
            }
        };

        ActionListener lineListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                movingPoint = null;
                movingSegment = new Line();
            }
        };

        pointBtn.addActionListener(pointListener);
        segmentBtn.addActionListener(segmentListener);
        lineBtn.addActionListener(lineListener);

        menu.add(pointBtn);
        menu.add(segmentBtn);
        menu.add(lineBtn);
        menu.add(mousePositionLabel);
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
            //System.out.println("PAINT");
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if(movingPoint != null)
                g2.drawOval(movingPoint.getX()-Point.width/2, movingPoint.getY()-Point.height/2,Point.width, Point.height);
            if(movingSegment != null && movingSegment.getX1()>=0 && movingSegment.getX2()>=0) {
                if (movingSegment instanceof Line) {
                    System.out.println("TRACE");
                    Line l = (Line) movingSegment;
                    Line2D line;
                    double coeff = l.getCoeff();

                    double y0 = (l.getY1() - (coeff * l.getX1()));
                    double x0 = 0.;
                    double xMax = this.getWidth();
                    double yMax = coeff * xMax + y0;
                    if(l.getX1() < l.getX2())
                        line = new Line2D.Double(x0, y0, xMax, yMax);
                    else line = new Line2D.Double(xMax, yMax, x0, y0);
                    g2.draw(line);
                }
                else
                    g2.draw(new Line2D.Double(movingSegment.getX1(), movingSegment.getY1(), movingSegment.getX2(), movingSegment.getY2()));
            }

            for(Point p : points) {
                g2.setColor(p.getColor());
                g2.drawOval(p.getX()-Point.width/2, p.getY()-Point.height/2, Point.width, Point.height);
                g2.fillOval(p.getX()-Point.width/2, p.getY()-Point.height/2, Point.width, Point.height);
            }

            for(Segment s : segments) {
                Line2D l = new Line2D.Double(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                g2.draw(l);
            }

            for(Line l : lines) {
                Line2D line;
                double coeff = l.getCoeff();

                double y0 = (l.getY1() - (coeff * l.getX1()));
                double x0 = 0.;
                double xMax = this.getWidth();
                double yMax = coeff * xMax + y0;
                if(l.getX1() < l.getX2())
                    line = new Line2D.Double(x0, y0, xMax, yMax);
                else line = new Line2D.Double(xMax, yMax, x0, y0);
                g2.draw(line);
            }
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            if(movingPoint != null)
                movingPoint.setLocation(mouseEvent.getX(), mouseEvent.getY());

            mousePositionLabel.setText(Integer.toString(mouseEvent.getX()) + ":" + Integer.toString(mouseEvent.getY()));
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            mousePositionLabel.setText(Integer.toString(mouseEvent.getX()) + ":" + Integer.toString(mouseEvent.getY()));
            if(movingPoint != null) movingPoint.setLocation(mouseEvent.getX(), mouseEvent.getY());
            if(movingSegment != null && movingSegment.getX1()>=0) {
                movingSegment.setLocation(movingSegment.getX1(), movingSegment.getY1(), mouseEvent.getX(), mouseEvent.getY());
            }

            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if(movingPoint != null) points.add(movingPoint);
            for(Point p : points) {
                if((Math.abs(mouseEvent.getX() - p.getX()) <= Point.width)
                        && (Math.abs(mouseEvent.getY() - p.getY()) <= Point.height))
                {
                    System.out.println("TOUCHE");
                    movingPoint = p;
                }
            }

            for(Segment s : segments) {
                Line2D l = new Line2D.Double(s.getX1(), s.getY1(), s.getX2(), s.getY2());
                /*if((Math.abs(mouseEvent.getX() - s.getX()) <= Segment.width)
                        && (Math.abs(mouseEvent.getY() - s.getY()) <= Point.height))*/
                if(l.intersects(mouseEvent.getX(), mouseEvent.getY(), Segment.width, Segment.height))
                {
                    System.out.println("TOUCHE");
                }
            }

            if(movingSegment != null && movingSegment.getX1()<0) {
                movingSegment.setLocation(mouseEvent.getX(), mouseEvent.getY(), movingSegment.getX2(), movingSegment.getY2());
            }
            else if(movingSegment != null && movingSegment.getX1()>=0) {
                movingSegment.setLocation(movingSegment.getX1(), movingSegment.getY1(), mouseEvent.getX(), mouseEvent.getY());
                if(movingSegment instanceof Line) {
                    lines.add((Line) movingSegment);
                }
                else segments.add(movingSegment);
            }

            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            if(movingSegment != null && movingSegment.getX1()>=0 && movingSegment.getX2()>=0) {
                if(movingSegment instanceof Segment)
                    movingSegment = new Segment();
                else movingSegment = new Line();
            }
            if(movingPoint != null) movingPoint = new Point();
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

}
