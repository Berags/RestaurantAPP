package edu.unifi.view;

import edu.unifi.controller.OrderController;
import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.OrderId;
import edu.unifi.model.entities.Table;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrderListItem extends DishItem {
    JLabel quantityLabel;
    OrderId oid;

    public OrderListItem(Dish dish, int orderQuantity, OrderId oid, TableUpdateTool tableUpdateTool, Table table) {

        super(dish);
        this.oid = oid;

        for (var a : deleteButton.getActionListeners())
            deleteButton.removeActionListener(a);

        for (var a : editButton.getActionListeners())
            editButton.removeActionListener(a);

        listPanel.remove(dishIdLabel);
        listPanel.remove(dishNameLabel);
        listPanel.remove(dishTypeLabel);
        listPanel.remove(actionTestPanel);
        listPanel.remove(spacer5);
        listPanel.remove(spacer6);

        listPanel.setLayout(new GridLayout(1,4));

        dishNameLabel.setText(dish.getName());
        dishNameLabel.setName("DishNameLabel" + dish.getId());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0.2;

        listPanel.add(dishNameLabel);

        quantityLabel = new JLabel(String.valueOf(orderQuantity));
        quantityLabel.setName("QuantityLabel" + dish.getId());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weighty = 0.2;

        listPanel.add(quantityLabel, gbc);

        float quantity = Float.parseFloat(quantityLabel.getText());
        float price = dish.getPrice();

        JLabel totalLabel = new JLabel(Float.toString(quantity * price / 100));
        totalLabel.setName("TotalLabel" + dish.getId());
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0.2;
        listPanel.add(totalLabel, gbc);

        actionTestPanel = new JPanel();
        actionTestPanel.setLayout(new GridLayout(1,2));

        JPanel rightActionTestPanel = new JPanel();
        rightActionTestPanel.setLayout(new BoxLayout(rightActionTestPanel, BoxLayout.PAGE_AXIS));
        rightActionTestPanel.add(Box.createVerticalGlue());
        JPanel leftActionTestPanel = new JPanel();
        leftActionTestPanel.setLayout(new BoxLayout(leftActionTestPanel, BoxLayout.PAGE_AXIS));
        leftActionTestPanel.add(Box.createVerticalGlue());

        editButton = new JButton();
        editButton.setHideActionText(false);
        editButton.setHorizontalAlignment(0);
        editButton.setHorizontalTextPosition(0);
        editButton.setIcon(FontIcon.of(MaterialDesignP.PENCIL, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        editButton.addActionListener(new OrderController.OrderEditController(oid, tableUpdateTool, table));
        leftActionTestPanel.add(editButton, gbc);
        leftActionTestPanel.add(Box.createVerticalGlue());
        actionTestPanel.add(leftActionTestPanel, gbc);

        deleteButton = new JButton();
        deleteButton.setHideActionText(false);
        deleteButton.setHorizontalAlignment(0);
        deleteButton.setHorizontalTextPosition(0);
        deleteButton.setIcon(FontIcon.of(MaterialDesignD.DELETE, 20));
        deleteButton.addActionListener(new OrderController.OrderDeletionController(oid, tableUpdateTool, table));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weighty = 0.2;

        rightActionTestPanel.add(deleteButton, gbc);
        rightActionTestPanel.add(Box.createVerticalGlue());
        actionTestPanel.add(rightActionTestPanel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        listPanel.add(actionTestPanel, gbc);

    }
}
