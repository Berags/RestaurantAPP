package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.UserEditDeletionToolController;
import edu.unifi.model.entities.User;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class UserItem {

    private JPanel listPanel;
    private User user;

    private JButton editButton;

    private JButton deleteButton;

    private JPanel actionTestPanel;

    private JLabel userNameSurnameLabel;

    private JLabel userUsernameLabel;

    private JLabel userEmailLabel;
    private JLabel userRoleLabel;

    private JPanel spacer1, spacer2;

    protected UserItem(User user, int index) {

        setUp(user,index);

        UserEditDeletionToolController.UserDeletionController userDeletionToolController = new UserEditDeletionToolController.UserDeletionController(user);

        try {
            userDeletionToolController.addObserver(Notifier.getInstance());
        } catch (Exception e) {}

        deleteButton.addActionListener(userDeletionToolController);
        actionTestPanel.add(deleteButton);
    }

    private void setUp(User user, int index){

        this.user = user;

        GridBagConstraints gbc;
        listPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        listPanel.setLayout(layout);
        userNameSurnameLabel = new JLabel();
        userNameSurnameLabel.setText(user.getName() + " " + user.getSurname());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(userNameSurnameLabel, gbc);

        spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        listPanel.add(spacer1, gbc);

        spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        listPanel.add(spacer2, gbc);

        userUsernameLabel = new JLabel();
        userUsernameLabel.setText(user.getUsername());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        listPanel.add(userUsernameLabel, gbc);

        userEmailLabel = new JLabel();
        userEmailLabel.setText(user.getEmail());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        listPanel.add(userEmailLabel, gbc);

        userRoleLabel = new JLabel();
        userRoleLabel.setText(user.getRole().toString());
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        listPanel.add(userRoleLabel, gbc);

        actionTestPanel = new JPanel();
        actionTestPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(actionTestPanel, gbc);

        editButton = new JButton();
        editButton.setName("EditUser");
        editButton.setHideActionText(false);
        editButton.setHorizontalAlignment(0);
        editButton.setHorizontalTextPosition(0);
        editButton.setIcon(FontIcon.of(MaterialDesignP.PENCIL, 20));
        editButton.addActionListener(e -> {
            try {
                UserUpdateTool.getInstance("User update tool", user, 400, 300).setVisible(true);
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
