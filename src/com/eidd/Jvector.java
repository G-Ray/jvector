package com.eidd;

import com.eidd.graphics.Graphic;
import com.eidd.view.UI;
import javax.swing.SwingUtilities;
import java.io.*;
import java.util.ArrayList;

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

    public static ArrayList load() {
        try {
            FileInputStream fis = new FileInputStream("graphics");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Graphic> graphics = (ArrayList<Graphic>) ois.readObject();
            ois.close();
            System.out.println("FILE OPENED");
            return graphics;
        } catch (IOException e) {
            return new ArrayList<Graphic>(); // return an empty list
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return new ArrayList<Graphic>();
        }
    }

    public static void save(ArrayList graphics) {

        try {
            FileOutputStream fos = new FileOutputStream("graphics");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(graphics);
            oos.close();
            fos.close();
            System.out.println("FILE SAVED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
