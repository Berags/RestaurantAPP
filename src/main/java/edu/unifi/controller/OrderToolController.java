package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.*;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.model.orm.dao.OrderDAO;
import edu.unifi.view.OrderCreationItem;
import edu.unifi.view.TableUpdateTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class OrderToolController {

    private static List<Dish> dishes;
    private static Check check;
    private static Table commonTable;
    private static TableUpdateTool tableUpdateTool;

    public List<Dish> getFilteredDishes(String filter) {
        dishes = DishDAO.getInstance().getAll();
        return dishes.stream().filter(dish -> dish.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class OrderCreationController extends Observable implements ActionListener {
        private Dish dish;
        private OrderCreationItem orderCreationItem;

        public OrderCreationController(TableUpdateTool tUpdateTool, Table table, Dish dish, OrderCreationItem orderCreationItem) {
            commonTable = table;
            this.dish = dish;
            this.orderCreationItem = orderCreationItem;
            tableUpdateTool = tUpdateTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //to check if the table has an open check associated
            check = CheckDAO.getInstance().getValidCheckByTable(commonTable);
            if (java.util.Objects.isNull(check)) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "You must create a new check"));
                return;
            }
            dish = DishDAO.getInstance().getById(dish.getId());

            OrderId oid = new OrderId(check, dish);
            Order order = new Order(oid);

            order.setQuantity((Integer) orderCreationItem.getQuantitySpinner().getValue());

            java.util.List<Order> tableOrders = OrderDAO.getInstance().getAllTableOrders(commonTable, check);

            boolean contained = false;

            for (var a : tableOrders) {
                if (Objects.equals(a.getId().getDish().getId(), dish.getId())) {
                    order.setQuantity(((Integer) orderCreationItem.getQuantitySpinner().getValue()).intValue() + a.getQuantity());
                    contained = true;
                }
            }
            if (contained) {
                OrderDAO.getInstance().update(order);
            } else {
                OrderDAO.getInstance().insert(order);
            }
            tableUpdateTool.buildOrdersList(commonTable);
        }

        public void setDish(Dish dish) {
            this.dish = dish;
        }
    }

    public static class OrderEditController implements ActionListener {
        private OrderId oid;

        public OrderEditController(OrderId oid, TableUpdateTool tableUpdateToolt, Table table) {
            this.oid = oid;
            commonTable = table;
            tableUpdateTool = tableUpdateToolt;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            SpinnerNumberModel sModel = new SpinnerNumberModel(OrderDAO.getInstance().getById(oid).getQuantity(), 0, 1000, 1);
            JSpinner spinner = new JSpinner(sModel);
            int option = JOptionPane.showOptionDialog(null, spinner, "Enter the new quantity",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);


            if (option == JOptionPane.OK_OPTION) {
                Order order = OrderDAO.getInstance().getById(oid);
                order.setQuantity(Integer.parseInt(spinner.getValue().toString()));
                OrderDAO.getInstance().update(order);
                tableUpdateTool.buildOrdersList(commonTable);
            }
        }
    }

    public static class OrderDeletionController implements ActionListener {

        private OrderId oid;

        public OrderDeletionController(OrderId oid, TableUpdateTool tableUpdateToolt, Table table) {
            this.oid = oid;
            commonTable = table;
            tableUpdateTool = tableUpdateToolt;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Order order = OrderDAO.getInstance().getById(oid);
            OrderDAO.getInstance().delete(order);
            tableUpdateTool.buildOrdersList(commonTable);
        }
    }
}
