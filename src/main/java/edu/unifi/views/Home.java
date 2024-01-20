package edu.unifi.views;

import edu.unifi.api.graphics.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Home extends Window {
    public Home(String title) throws Exception {
        super(title, true, JFrame.EXIT_ON_CLOSE, 0, 0, 1000, 700);

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
        optionsMenu.add(settings);
        optionsMenu.add(exitFromApplication);

        JMenuItem createTableMenuItem = new JMenuItem("Create Table");
        createTableMenuItem.addActionListener(e -> {
            try {
                new TableCreationTool();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        tablesMenu.add(createTableMenuItem);

        addMenuEntries(new JMenu[]{optionsMenu, tablesMenu, dishesMenu});
    }
}
