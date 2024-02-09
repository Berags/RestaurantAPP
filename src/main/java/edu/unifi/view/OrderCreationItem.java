package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.OrderController;
import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.Table;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class OrderCreationItem extends DishItem {

    private JSpinner quantitySpinner;
    private TableUpdateTool tableUpdateTool;

    public OrderCreationItem(TableUpdateTool tableUpdateTool, Table table, Dish dish, int index) {
        super(dish);
        actionTestPanel.remove(editButton);
        actionTestPanel.remove(deleteButton);
        this.tableUpdateTool = tableUpdateTool;

        GridBagConstraints gbc;
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        quantitySpinner.setMinimumSize(new Dimension(70, 30));
        quantitySpinner.setPreferredSize(new Dimension(70, 30));
        quantitySpinner.setName("Quantity" + dish.getId());
        actionTestPanel.add(quantitySpinner, gbc);

        JButton addOrderButton = new JButton();
        addOrderButton.setName("Add" + dish.getId());
        FontIcon createFontIcon = FontIcon.of(MaterialDesignP.PLUS_BOX_OUTLINE, 20);
        addOrderButton.setIcon(createFontIcon);

        OrderController.OrderCreationController orderCreationController = new OrderController.OrderCreationController(tableUpdateTool, table, dish, this);
        orderCreationController.setDish(dish);

        try {
            orderCreationController.addObserver(Notifier.getInstance());
        } catch (Exception e) {
        }

        addOrderButton.addActionListener(orderCreationController);
        actionTestPanel.add(addOrderButton);
        listPanel.add(actionTestPanel);
    }

    public JSpinner getQuantitySpinner() {
        return quantitySpinner;
    }

    public JPanel getListPanel() {
        return listPanel;
    }
}
