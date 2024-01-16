package edu.unifi.view;

import edu.unifi.api.graphics.Window;

import javax.swing.*;
import java.awt.*;

public class Home extends Window {
    public Home(String title) throws Exception {
        super(title, true, JFrame.EXIT_ON_CLOSE);

        JPanel rootPane = new JPanel();
        setRootLayout(Layout.BORDER, 0, 0);

        JTabbedPane roomsTabbedPane = new JTabbedPane();
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        roomsTabbedPane.addTab("Untitled", panel1);
        addComponent(roomsTabbedPane, BorderLayout.CENTER);

        JLabel restaurantNamePane = new JLabel();
        restaurantNamePane.setHorizontalAlignment(0);
        restaurantNamePane.setText("restaurantName");
        restaurantNamePane.setToolTipText("Restaurant Name");
        addComponent(restaurantNamePane, BorderLayout.NORTH);

        /* MENU*/
        JMenu optionsMenu = new JMenu("Options");
        JMenu tablesMenu = new JMenu("Tables");
        JMenu dishesMenu = new JMenu("Dishes");

        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem exitFromApplication = new JMenuItem("Exit");
        optionsMenu.add(exitFromApplication);
        optionsMenu.add(settings);

        addMenuEntries(optionsMenu, tablesMenu, dishesMenu);
    }
}
