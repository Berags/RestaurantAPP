package edu.unifi.view;

import edu.unifi.controller.OrderController;
import edu.unifi.model.entities.Table;

import javax.swing.*;
import java.awt.*;

public class OrderCreationTool extends DishView {

    private OrderController orderController;

    private TableUpdateTool tableUpdateTool;

    private Table table;

    public OrderCreationTool(OrderController orderController, TableUpdateTool tableUpdateTool, Table table) throws Exception{
        super();
        this.orderController = orderController;
        this.tableUpdateTool = tableUpdateTool;
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
            OrderCreationItem OCI = new OrderCreationItem(tableUpdateTool,table,d,index);
            this.listPanel.add(OCI.getListPanel());
            index++;
        }

        listScroller.setViewportView(listPanel);
        panel2.add(listScroller, BorderLayout.CENTER);
    }
}
