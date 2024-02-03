package edu.unifi.view;

import edu.unifi.controller.OrderController;
import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.Table;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class OrderCreationItem extends DishItem{

    private JSpinner quantitySpinner;

    public OrderCreationItem(Table table, Dish dish, int index){
        super(dish);
        actionTestPanel.remove(editButton);
        actionTestPanel.remove(deleteButton);

        GridBagConstraints gbc;
        quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        quantitySpinner.setMinimumSize(new Dimension(70,30));
        quantitySpinner.setPreferredSize(new Dimension(70,30));
        actionTestPanel.add(quantitySpinner,gbc);

        JButton addOrderButton = new JButton();
        FontIcon createFontIcon = FontIcon.of(MaterialDesignP.PLUS_BOX_OUTLINE, 20);
        addOrderButton.setIcon(createFontIcon);

        OrderController.OrderCreationController orderCreationController = new OrderController.OrderCreationController(table, dish,this);
        orderCreationController.setDish(dish);
        addOrderButton.addActionListener(orderCreationController);
        actionTestPanel.add(addOrderButton);
    }

    public JSpinner getQuantitySpinner() {
        return quantitySpinner;
    }
}
