package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.RoomToolController;
import edu.unifi.model.entities.Room;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class RoomItem {
    protected JPanel listPanel;
    protected JButton deleteButton;
    protected JPanel actionTestPanel;
    protected JButton editButton;
    protected JLabel roomNameLabel;
    protected JLabel numberOfTablesLabel;
    protected JPanel spacer5;
    protected JPanel spacer6;

    private Room room;


    RoomItem(Room room, int index) {

        setUp(room,index);

        RoomToolController.RoomDeletionController roomDeletionController = new RoomToolController.RoomDeletionController(room);

        try {
            Notifier notifier = Notifier.getInstance();
            roomDeletionController.addObserver(notifier);
        } catch (Exception e) {}

        deleteButton.addActionListener(roomDeletionController);
        actionTestPanel.add(deleteButton);
    }

    private void setUp(Room room, int index){
        this.room = room;
        GridBagConstraints gbc;
        listPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        listPanel.setLayout(layout);
        roomNameLabel = new JLabel();
        roomNameLabel.setText(room.getName());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(roomNameLabel, gbc);
        spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        listPanel.add(spacer5, gbc);
        spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        listPanel.add(spacer6, gbc);
        numberOfTablesLabel = new JLabel();
        numberOfTablesLabel.setText(String.valueOf(room.getNumberOfTables()));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        listPanel.add(numberOfTablesLabel, gbc);

        actionTestPanel = new JPanel();
        actionTestPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(actionTestPanel, gbc);

        editButton = new JButton();
        editButton.setHideActionText(false);
        editButton.setHorizontalAlignment(0);
        editButton.setHorizontalTextPosition(0);
        editButton.setIcon(FontIcon.of(MaterialDesignP.PENCIL, 20));
        editButton.addActionListener(e -> {
            try {
                RoomUpdateTool.getInstance("Room update tool", room, 400, 300).setVisible(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        actionTestPanel.add(editButton);

        deleteButton = new JButton();
        deleteButton.setHideActionText(false);
        deleteButton.setHorizontalAlignment(0);
        deleteButton.setHorizontalTextPosition(0);
        deleteButton.setIcon(FontIcon.of(MaterialDesignD.DELETE, 20));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = index++;
        gbc.weighty = 0.1;
        listPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
    }

    JPanel getListPanel() {
        return listPanel;
    }

}