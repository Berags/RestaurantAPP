package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.TableDeletionToolController;
import edu.unifi.model.entities.Table;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class TableDeletionTool extends Window {
    private JComboBox<String> roomComboBox;
    private JComboBox<String> tableComboBox;
    private final JButton removeButton = new JButton();
    private JLabel titleLabel;
    private JLabel roomLabel;
    private JLabel tableLabel;
    private final HashMap<String, java.util.List<Table>> roomTableHashMap = new HashMap<>();
    private final TableDeletionToolController tableDeletionToolController;

    private static TableDeletionTool instance;

    private TableDeletionTool() throws Exception {
        super("Delete", false, JFrame.DISPOSE_ON_CLOSE, 0, 0, 400, 300);

        tableDeletionToolController = new TableDeletionToolController(this);
        tableDeletionToolController.addObserver(Notifier.getInstance());

        removeButton.addActionListener(tableDeletionToolController);

        for (var room : tableDeletionToolController.getRooms()) {
            roomTableHashMap.put(room.getName(), room.getTables());
        }

        setUpUI();
    }

    private void setUpUI() throws Exception {
        setRootLayout(Layout.BORDER);
        titleLabel = new JLabel();
        Font titleLabelFont = getFont(null, Font.BOLD, 20, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setText("Table Deletion Tool");
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        roomLabel = new JLabel();
        Font roomLabelFont = getFont(null, Font.BOLD, 18, roomLabel.getFont());
        if (roomLabelFont != null) roomLabel.setFont(roomLabelFont);
        roomLabel.setText("Room");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(roomLabel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer2, gbc);
        roomComboBox = new JComboBox<>();
        for (var room : roomTableHashMap.entrySet()) {
            roomComboBox.addItem(room.getKey());
        }

        roomComboBox.addActionListener(e -> {
            tableComboBox.removeAllItems();
            for (var table : roomTableHashMap.get((String) roomComboBox.getSelectedItem())) {
                tableComboBox.addItem(table.getName());
            }
        });

        Font roomComboBoxFont = getFont(null, -1, 18, roomComboBox.getFont());
        if (roomComboBoxFont != null) roomComboBox.setFont(roomComboBoxFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(roomComboBox, gbc);
        tableComboBox = new JComboBox<>();
        for (var table : tableDeletionToolController.getRoomByName((String) roomComboBox.getSelectedItem()).getTables()) {
            tableComboBox.addItem(table.getName());
        }
        Font tableComboBoxFont = getFont(null, -1, 18, tableComboBox.getFont());
        if (tableComboBoxFont != null) tableComboBox.setFont(tableComboBoxFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(tableComboBox, gbc);
        tableLabel = new JLabel();
        Font tableLabelFont = getFont(null, Font.BOLD, 18, tableLabel.getFont());
        if (tableLabelFont != null) tableLabel.setFont(tableLabelFont);
        tableLabel.setText("Table");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(tableLabel, gbc);
        Font removeButtonFont = getFont(null, Font.BOLD, 18, removeButton.getFont());
        if (removeButtonFont != null) removeButton.setFont(removeButtonFont);
        removeButton.setText("Remove");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(removeButton, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer3, gbc);
        roomLabel.setLabelFor(roomComboBox);
        tableLabel.setLabelFor(tableComboBox);

        addComponent(titleLabel, BorderLayout.NORTH);
        addComponent(panel1, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * To implement the Singleton: we don't want to create a window
     * every time the user clicks on "remove table", we need only one.
     * @return
     * @throws Exception
     */
    public static TableDeletionTool getInstance() throws Exception {
        TableDeletionTool thisInstance = instance;
        if (instance == null) {
            synchronized (TableDeletionTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new TableDeletionTool();
            }
        }
        return thisInstance;
    }

    /**
     * to "reset" the Singleton
     */
    @Override
    public void dispose() {
        instance = null;
        super.dispose();
    }

    public Long getSelectedTableId() {
        return roomTableHashMap.get((String) roomComboBox.getSelectedItem()).get(tableComboBox.getSelectedIndex()).getId();
    }

    public String getTableRoom() {
        return (String) roomComboBox.getSelectedItem();
    }
}
