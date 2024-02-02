package edu.unifi.view;

import edu.unifi.controller.OrderController;

import javax.swing.*;
import java.awt.*;

public class OrderCreationTool extends DishView {

    private OrderController orderController;

    public OrderCreationTool(OrderController orderController) throws Exception{
        super();
        this.orderController = orderController;
        getPanel1().remove(getAddButton());
        buildList();
        setVisible(true);
    }

    @Override
    public void buildList(){
        filteredDishes = orderController.getFilteredDishes(searchTextField.getText() == null ? "" : searchTextField.getText());
        listPanel = new JPanel(new GridLayout(filteredDishes.size(), 1));
        int index = 0;

        for (var d : filteredDishes) {
            OrderCreationItem OCI = new OrderCreationItem(d,index);
            this.listPanel.add(OCI.getListPanel());
            index++;
        }

        listScroller.setViewportView(listPanel);
        panel2.add(listScroller, BorderLayout.CENTER);
    }
}
