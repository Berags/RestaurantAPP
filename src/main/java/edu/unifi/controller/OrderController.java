package edu.unifi.controller;

import edu.unifi.model.entities.*;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.model.orm.dao.OrderDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.view.OrderCreationItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderController {

    private static List<Dish> dishes;

    public List<Dish> getFilteredDishes(String filter) {
        dishes = DishDAO.getInstance().getAll();
        return dishes.stream().filter(dish -> dish.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class OrderCreationController implements ActionListener {
        private Dish dish;
        private Table table;

        private OrderCreationItem orderCreationItem;

        public OrderCreationController(Table table, Dish dish, OrderCreationItem orderCreationItem) {
            this.table = table;
            this.dish = dish;
            this.orderCreationItem = orderCreationItem;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Add button to create check
            Check check;
            table = TableDAO.getInstance().getById(table.getId());
            dish = DishDAO.getInstance().getById(dish.getId());
            check = new Check();
            check.setIssueDate(java.time.LocalDateTime.now());
            check.setTable(table);
            table.getChecks().add(check);

            CheckDAO.getInstance().insert(check);

            OrderId oid = new OrderId(check, dish);
            Order order = new Order(oid);

            order.setQuantity((Integer) orderCreationItem.getQuantitySpinner().getValue());
            OrderDAO.getInstance().insert(order);
        }

        public void setDish(Dish dish) {
            this.dish = dish;
        }
    }
}
