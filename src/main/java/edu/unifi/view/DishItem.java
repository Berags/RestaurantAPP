package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.DishController;
import edu.unifi.model.entities.Dish;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class DishItem {

    protected   JPanel listPanel;
    private  Dish dish;

    protected JButton editButton;

    protected JButton deleteButton;

    protected JPanel actionTestPanel;

    protected JLabel dishIdLabel;

    protected JLabel dishNameLabel;

    protected JLabel dishTypeLabel;

    protected JPanel spacer5,spacer6;

    DishItem(Dish d, int index) {

        setUp(d,index);

        DishController.DishDeletionController dishDeletionController = new DishController.DishDeletionController(dish);

        try {
            Notifier notifier = Notifier.getInstance();
            notifier.setDishView(DishView.getInstance(new DishController()));
            dishDeletionController.addObserver(notifier);
        } catch (Exception e) {}

        deleteButton.addActionListener(dishDeletionController);
        actionTestPanel.add(deleteButton);
    }

    DishItem(Dish d){
        int index = 0;
        setUp(d, index);
    }

    private void setUp(Dish d, int index){
        this.dish = d;
        GridBagConstraints gbc;
        listPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        listPanel.setLayout(layout);
        dishIdLabel = new JLabel();
        dishIdLabel.setText(d.getId().toString());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(dishIdLabel, gbc);
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
        dishNameLabel = new JLabel();
        dishNameLabel.setText(d.getName());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        listPanel.add(dishNameLabel, gbc);
        dishTypeLabel = new JLabel();
        dishTypeLabel.setText(d.getTypeOfCourse().getName());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        listPanel.add(dishTypeLabel, gbc);
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
                DishUpdateTool.getInstance("Dish update tool", d, 400, 300).setVisible(true);
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
