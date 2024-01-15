package edu.unifi.api.graphics;

import javax.swing.*;
import java.util.ArrayList;

public class Window extends JFrame implements Runnable {
    private final Thread t = new Thread(this);
    private final ArrayList<JComponent> components;
    private final JPanel rootPane = new JPanel();

    public Window(String title, ArrayList<JComponent> components) {
        super(title);

        this.components = (ArrayList<JComponent>) components.clone();

        getContentPane().add(rootPane);
        for (var component : this.components) {
            rootPane.add(component);
        }

        setResizable(true);
        setVisible(true);
        setBounds(0, 0, 1280, 720);
        //TODO: add to class
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        t.setName(title);
    }

    public void addComponent(JComponent component) {
        this.components.add(component);
        System.out.println("Adding component");
        rootPane.removeAll();
        for (var c : this.components) {
            rootPane.add(c);
        }
        update();
    }

    private void update() {
        invalidate();
        validate();
        repaint();
    }

    @Override
    public void run() {
        // Do we really need a thread?
    }
}
