package edu.unifi.view;

import edu.unifi.api.graphics.Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Home extends Window {
    public Home(String title) throws HeadlessException {
        super(title, createComponents());
    }

    private static ArrayList<JComponent> createComponents() {
        ArrayList<JComponent> components = new ArrayList<>();

        JLabel testLabel = new JLabel("Ciao");
        testLabel.setHorizontalAlignment(JLabel.CENTER);
        testLabel.setFont(new Font("Courier New", Font.BOLD, 32));
        testLabel.setForeground(Color.RED);
        testLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        components.add(testLabel);

        JLabel test2 = new JLabel("Prova?");
        test2.setHorizontalAlignment(JLabel.LEFT);
        test2.setFont(new Font("Courier New", Font.BOLD, 32));
        test2.setForeground(Color.RED);
        test2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        components.add(test2);

        return components;
    }
}
