package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.ExitController;
import edu.unifi.controller.TableController;
import edu.unifi.model.entities.Room;
import edu.unifi.model.orm.dao.RoomDAO;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Home extends Window {
    private JTabbedPane roomsTabbedPane;
    private java.util.List<Room> rooms;

    public Home(String title) throws Exception {
        super(title, true, JFrame.EXIT_ON_CLOSE, 0, 0, 1000, 700);

        setRootLayout(Layout.BORDER, 0, 0);

        roomsTabbedPane = new JTabbedPane();
        ArrayList<JPanel> panels = new ArrayList<>();
        rooms = RoomDAO.getInstance().getAll();
        for (var room : rooms) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            roomsTabbedPane.addTab(room.getName(), panel);
            panels.add(panel);
        }
        if (!rooms.isEmpty())
            showTables();
        roomsTabbedPane.addChangeListener(e -> showTables());
        addComponent(roomsTabbedPane, BorderLayout.CENTER);

        /* MENU*/
        JMenu optionsMenu = new JMenu("Options");
        JMenu tablesMenu = new JMenu("Tables");
        JMenu dishesMenu = new JMenu("Dishes");

        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem exitFromApplication = new JMenuItem("Exit");

        //menu option to exit the program
        ExitController exitController = new ExitController();
        exitController.addObserver(Notifier.getInstance());
        exitFromApplication.addActionListener(exitController);

        optionsMenu.add(settings);
        optionsMenu.add(exitFromApplication);

        //menu option to add a new table
        JMenuItem addTableMenuItem = new JMenuItem("Add Table");
        addTableMenuItem.addActionListener(e -> {
            try {
                //new TableCreationTool();
                TableCreationTool.getInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        tablesMenu.add(addTableMenuItem);

        //menu option to remove a table
        JMenuItem removeTableMenuItem = new JMenuItem("Remove Table");
        removeTableMenuItem.addActionListener(e -> {
            try {
                new TableDeletionTool();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        tablesMenu.add(removeTableMenuItem);


        //menu option to add a new dish
        JMenuItem addDishItem = new JMenuItem("Add Dish");
        addDishItem.addActionListener(e -> {
            try {
                DishCreationTool.getInstance();
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

        Notifier.getInstance().setHome(this);
    }

    public void showTables() {
        System.out.println("Update the tables view");
        Room room = rooms.get(roomsTabbedPane.getSelectedIndex());
        room = RoomDAO.getInstance().getById(room.getName());
        JPanel panel = (JPanel) roomsTabbedPane.getSelectedComponent();
        panel.removeAll();
        for (var table : room.getTables()) {
            Button button = new Button(table.getName());
            button.addActionListener(e -> {
                try {
                    new TableUpdateTool(table);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            button.setBackground(table.getState().getColor());
            panel.add(button);
        }
    }

    public void showResultDialog(String message, boolean messageType) {
        JOptionPane.showMessageDialog(null, message, messageType ? "Action successful" : "Severe Error!",
                messageType ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE,
                messageType ? FontIcon.of(MaterialDesignC.CHECK_CIRCLE_OUTLINE, 40, Color.BLUE) : FontIcon.of(MaterialDesignA.ALERT_RHOMBUS_OUTLINE, 40, Color.RED));
    }
}
