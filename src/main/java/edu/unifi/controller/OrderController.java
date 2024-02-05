package edu.unifi.controller;

import edu.unifi.model.entities.*;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.model.orm.dao.OrderDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.view.OrderCreationItem;
import edu.unifi.view.OrderListItem;
import edu.unifi.view.TableUpdateTool;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class OrderController {

    private static List<Dish> dishes;
    private static Check check;

    private static TableUpdateTool tableUpdateTool;

    public List<Dish> getFilteredDishes(String filter) {
        dishes = DishDAO.getInstance().getAll();
        return dishes.stream().filter(dish -> dish.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class OrderCreationController implements ActionListener {
        private Dish dish;
        private Table table;

        private OrderCreationItem orderCreationItem;

        public OrderCreationController(TableUpdateTool tUpdateTool, Table table, Dish dish, OrderCreationItem orderCreationItem) {
            this.table = table;
            this.dish = dish;
            this.orderCreationItem = orderCreationItem;
            tableUpdateTool = tUpdateTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            check = CheckDAO.getInstance().getCheckByTable(table);
            if(!java.util.Objects.isNull(check)){
                dish = DishDAO.getInstance().getById(dish.getId());

                OrderId oid = new OrderId(check, dish);
                Order order = new Order(oid);

                order.setQuantity((Integer) orderCreationItem.getQuantitySpinner().getValue());

                java.util.List<Order> tableOrders = OrderDAO.getInstance().getAllTableOrders(table,check);

                boolean contained = false;

                for(var a : tableOrders){
                    if(Objects.equals(a.getId().getDish().getId(), dish.getId())){
                        order.setQuantity((Integer) orderCreationItem.getQuantitySpinner().getValue() + a.getQuantity());
                        contained = true;
                    }
                }
                if(contained){
                    OrderDAO.getInstance().update(order);
                }else OrderDAO.getInstance().insert(order);


                OrderListItem orderListItem = new OrderListItem(dish, new OrderCreationItem(tableUpdateTool,table,dish,0));
                tableUpdateTool.getOrderItems().add(orderListItem);
                tableUpdateTool.buildOrdersList(table);


            }else{
                //TODO:uniform with others and passing through notifier?
                JOptionPane.showMessageDialog(null,"You must create a new check", "Error!",JOptionPane.INFORMATION_MESSAGE);
            }

        }

        public void setDish(Dish dish) {
            this.dish = dish;
        }
    }

    public static class CheckCreationController implements ActionListener{

        private Table table;

        public CheckCreationController(Table table){
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(java.util.Objects.isNull(CheckDAO.getInstance().getCheckByTable(table))) {
                table = TableDAO.getInstance().getById(table.getId());
                check = new Check();
                check.setIssueDate(java.time.LocalDateTime.now());
                check.setTable(table);
                table.getChecks().add(check);
                CheckDAO.getInstance().insert(check);

                //TODO:uniform with others and passing through notifier?
                JOptionPane.showMessageDialog(null,"New check created successfully", "Attention!",JOptionPane.INFORMATION_MESSAGE);

            }else {
                //TODO:uniform with others and passing through notifier?
                JOptionPane.showMessageDialog(null,"This table already has an associated check.\n " +
                                "You can reset it using the decicated button", "Attention!",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
