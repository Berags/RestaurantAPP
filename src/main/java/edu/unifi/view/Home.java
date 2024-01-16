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
        final JPanel panelRoom1 = new JPanel();
        final JPanel panelRoom2 = new JPanel();

        panelRoom1.setLayout(new BorderLayout(0, 0));
        roomsTabbedPane.addTab("Room 1", panelRoom1);
        panelRoom2.setLayout(new BorderLayout(0, 0));
        roomsTabbedPane.addTab("Room 2", panelRoom2);
        addComponent(roomsTabbedPane, BorderLayout.CENTER);

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
