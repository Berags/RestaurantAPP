package edu.unifi.view;

import javax.swing.*;
import java.awt.*;

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

        //menu option to exit the program
        exitFromApplication.addActionListener(e -> {
            try {
                System.exit(0);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        optionsMenu.add(settings);
        optionsMenu.add(exitFromApplication);

        //menu option to add a new table
        JMenuItem addTableMenuItem = new JMenuItem("Add Table");
        addTableMenuItem.addActionListener(e -> {
            try {
                new TableCreationTool();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        tablesMenu.add(addTableMenuItem);

        //menu option to remove a table
        JMenuItem removeTableMenuItem = new JMenuItem("Remove Table");


        tablesMenu.add(removeTableMenuItem);



        //menu option to add a new dish
        JMenuItem addDishItem = new JMenuItem("Add Dish");
        addDishItem.addActionListener(e -> {
            try {
                new DishCreationTool();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        JMenuItem removeDishItem = new JMenuItem("Remove Dish");
        JMenuItem EditDishItem = new JMenuItem("Edit Dish");

        dishesMenu.add(addDishItem);
        dishesMenu.add(removeDishItem);
        dishesMenu.add(EditDishItem);

        addMenuEntries(new JMenu[]{optionsMenu, tablesMenu, dishesMenu});
        setVisible(true);
    }
}
