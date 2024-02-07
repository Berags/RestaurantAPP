package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.DishController;
import edu.unifi.controller.ExitController;
import edu.unifi.controller.HomeController;
import edu.unifi.controller.RoomEditDeletionToolController;
import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.Table;
import edu.unifi.model.entities.User;
import edu.unifi.model.util.security.Roles;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Home extends Window {
    private JTabbedPane roomsTabbedPane;
    private java.util.List<Room> rooms;
    private final HomeController homeController;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final JLabel databaseMenu = new JLabel("Connected");
    private String username;

    public Home(String title, String username) throws Exception {
        super(title, true, JFrame.EXIT_ON_CLOSE, 0, 0, 1000, 700);
        this.username = username;
        homeController = new HomeController(this);
        scheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.MINUTES);

        setRootLayout(Layout.BORDER, 0, 0);

        updateHomeRooms();

        /* MENU*/
        JMenu optionsMenu = new JMenu("Options");


        databaseMenu.setIcon(FontIcon.of(MaterialDesignD.DATABASE_CHECK, 20, Color.GREEN));
        databaseMenu.setPreferredSize(new Dimension(100, 20));

        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem users = new JMenuItem("Users");
        JMenuItem exitFromApplication = new JMenuItem("Exit");

        //menu option to exit the program
        ExitController exitController = new ExitController();
        exitController.addObserver(Notifier.getInstance());
        exitFromApplication.addActionListener(exitController);

        optionsMenu.add(settings);
        optionsMenu.add(users);
        optionsMenu.add(exitFromApplication);



        if (homeController.getUserRoleByUsername(username).getRole() == Roles.ADMIN) {
            JMenu tablesMenu = new JMenu("Tables");
            JMenu dishesMenu = new JMenu("Menu");
            JMenu roomsMenu = new JMenu("Rooms");
            //menu option to add a new dish
            JMenuItem addDishItem = new JMenuItem("Add Dish");
            addDishItem.addActionListener(e -> {
                try {
                    DishCreationTool.getInstance("Dish creation tool",400,300);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });


            JMenuItem editDishItem = new JMenuItem("Edit Menu");
            editDishItem.addActionListener(e -> {
                try {
                    DishController dishController = new DishController();
                    DishView.getInstance(dishController).buildList();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            //menu option to add a new table
            JMenuItem addTableMenuItem = new JMenuItem("Add Table");
            addTableMenuItem.addActionListener(e -> {
                try {
                    TableCreationTool.getInstance("Table creation tool",400,300);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            //menu option to remove a table
            JMenuItem removeTableMenuItem = new JMenuItem("Remove Table");
            removeTableMenuItem.addActionListener(e -> {
                try {
                    TableDeletionTool.getInstance();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            JMenuItem addRoomMenuItem = new JMenuItem("Add Room");
            addRoomMenuItem.addActionListener(e -> {
                try {
                    RoomCreationTool.getInstance("Room creation tool", 400, 300);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            JMenuItem editRoomMenuItem = new JMenuItem("Edit Room");
            editRoomMenuItem.addActionListener(e -> {
                try {
                    RoomEditDeletionToolController roomEditDeletionToolController = new RoomEditDeletionToolController();
                    RoomView.getInstance(roomEditDeletionToolController).buildList();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            tablesMenu.add(addTableMenuItem);
            tablesMenu.add(removeTableMenuItem);
            dishesMenu.add(addDishItem);
            dishesMenu.add(editDishItem);
            roomsMenu.add(addRoomMenuItem);
            roomsMenu.add(editRoomMenuItem);

            addMenuEntries(new Component[]{optionsMenu, tablesMenu, dishesMenu,roomsMenu, Box.createHorizontalGlue(), databaseMenu});
        }
        else if (homeController.getUserRoleByUsername(username).getRole() == Roles.WAITER)
            addMenuEntries(new Component[]{optionsMenu, Box.createHorizontalGlue(), databaseMenu});

        setVisible(true);

        Notifier.getInstance().setHome(this);
    }

    public void updateRoom() {
        Room room = rooms.get(roomsTabbedPane.getSelectedIndex());
        room = homeController.getById(room.getName());
        JPanel panel = (JPanel) roomsTabbedPane.getSelectedComponent();
        panel.removeAll();
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        topPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        for (var table : room.getTables()) {
            topPanel.add(createButton(table));
        }
        panel.add(topPanel, BorderLayout.CENTER);
    }

    public void updateHomeRooms() {

        if (!java.util.Objects.isNull(roomsTabbedPane)){
            roomsTabbedPane.setVisible(false);
            remove(roomsTabbedPane);
        }
        roomsTabbedPane = new JTabbedPane();
        rooms = homeController.getRooms();
        for (var room : rooms) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(10, 10));
            panel.setPreferredSize(new Dimension(1000, 700));
            roomsTabbedPane.addTab(room.getName(), panel);
        }
        if (!rooms.isEmpty())
            updateRoom();
        roomsTabbedPane.addChangeListener(e -> updateRoom());
        roomsTabbedPane.setVisible(true);
        addComponent(roomsTabbedPane, BorderLayout.CENTER);
    }

    private static JButton createButton(Table table) {
        JButton button = new JButton("<html><b>" + table.getName() + "</b><br>Seats: " + table.getNOfSeats() + "<br>" + table.getState().toString() + "</html>");
        button.setPreferredSize(new Dimension(180 + 35 * table.getNOfSeats(), 80));
        button.setHorizontalAlignment(JButton.CENTER);
        button.setVerticalAlignment(JButton.CENTER);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
        button.setBackground(table.getState().getColor());
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(table.getState().getColor().darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(table.getState().getColor());
            }
        });
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setIcon(FontIcon.of(MaterialDesignT.TABLE_CHAIR, 40, Color.BLACK));
        button.addActionListener(e -> {
            try {
                new TableUpdateTool("Table update tool", table ,900,700);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    public void showResultDialog(String message, boolean messageType) {
        JOptionPane.showMessageDialog(null, message, messageType ? "Action successful" : "Severe Error!",
                messageType ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE,
                messageType ? FontIcon.of(MaterialDesignC.CHECK_CIRCLE_OUTLINE, 40, Color.BLUE) : FontIcon.of(MaterialDesignA.ALERT_RHOMBUS_OUTLINE, 40, Color.RED));
    }

    @Override
    public void run() {
        databaseMenu.setIcon(FontIcon.of(MaterialDesignD.DATABASE_CHECK, 20, Color.BLUE));
        databaseMenu.setText("Updating...");
        updateRoom();
        databaseMenu.setIcon(FontIcon.of(MaterialDesignD.DATABASE_CHECK, 20, Color.GREEN));
        databaseMenu.setText("Connected");
    }
}
