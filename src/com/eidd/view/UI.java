package com.eidd.view;

import com.eidd.graphics.Point;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class UI extends JFrame {

    private JButton pointBtn,
                    lineBtn;

    private JLabel mousePositionLabel;
    private ArrayList<Point> points;
    private Point movingPoint;

    public UI() {
        points = new ArrayList<Point>();

        setTitle("Jvector");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 800));
        setVisible(true);

        JMenuBar menu = new JMenuBar();
        final Canvas canvas = new Canvas();

        pointBtn = new JButton("Point");
        lineBtn = new JButton("Line");

        mousePositionLabel = new JLabel();

        pointBtn.setEnabled(true);
        lineBtn.setEnabled(true);

        ActionListener pointListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("point");
                movingPoint = new Point();
            }
        };

        ActionListener lineListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("line");
                for(Point p : points) {
                    System.out.println(p.getX());
                }
            }
        };

        pointBtn.addActionListener(pointListener);
        lineBtn.addActionListener(lineListener);

        menu.add(pointBtn);
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

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            if(movingPoint != null) movingPoint.setLocation(mouseEvent.getX(), mouseEvent.getY());
            mousePositionLabel.setText(Integer.toString(mouseEvent.getX()) + ":" + Integer.toString(mouseEvent.getY()));
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            mousePositionLabel.setText(Integer.toString(mouseEvent.getX()) + ":" + Integer.toString(mouseEvent.getY()));
            if(movingPoint != null) movingPoint.setLocation(mouseEvent.getX(), mouseEvent.getY());
            repaint();
        }

        public void paint(Graphics g) {
            super.paintComponent(g);
            System.out.println("PAINT");
            Graphics2D g2 = (Graphics2D) g;

            if(movingPoint != null)
                g2.drawOval(movingPoint.getX()-Point.width/2, movingPoint.getY()-Point.height/2,Point.width, Point.height);

            for(Point p : points) {
                g2.drawOval(p.getX()-Point.width/2, p.getY()-Point.height/2, Point.width, Point.height);
                g2.fillOval(p.getX()-Point.width/2, p.getY()-Point.height/2, Point.width, Point.height);
            }
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
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            movingPoint = null;
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

}
