package edu.unifi.view;

import edu.unifi.controller.OrderController;
import edu.unifi.model.entities.Table;

import javax.swing.*;
import java.awt.*;

public class OrderCreationTool extends DishView {

    private OrderController orderController;
    private Table table;

    public OrderCreationTool(OrderController orderController, Table table) throws Exception{
        super();
        this.orderController = orderController;
        this.table = table;
        panel1.remove(addButton);
        buildList();
        setVisible(true);
    }

    @Override
    public void buildList(){
        filteredDishes = orderController.getFilteredDishes(searchTextField.getText() == null ? "" : searchTextField.getText());
        listPanel = new JPanel(new GridLayout(filteredDishes.size(), 1));
        int index = 0;

        for (var d : filteredDishes) {
            OrderCreationItem OCI = new OrderCreationItem(table,d,index);
            this.listPanel.add(OCI.getListPanel());
            index++;
        }

        listScroller.setViewportView(listPanel);
        panel2.add(listScroller, BorderLayout.CENTER);
    }
}
