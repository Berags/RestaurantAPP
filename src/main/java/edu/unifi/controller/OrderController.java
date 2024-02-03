package edu.unifi.controller;

import edu.unifi.model.entities.*;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.model.orm.dao.OrderDAO;
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

        public OrderCreationController(Table table, Dish dish, OrderCreationItem orderCreationItem){
            this.table = table;
            this.dish = dish;
            this.orderCreationItem = orderCreationItem;

        }

        @Override
        public void actionPerformed(ActionEvent e){

            Check check;

            if(table.getChecks().isEmpty()){
                check = new Check();
                check.setIssueDate(java.time.LocalDateTime.now());
                check.setTable(table);
                table.getChecks().add(check);
            }else check = table.getChecks().getLast();

            CheckDAO.getInstance().insert(check);

            Order order = new Order();



            //OrderId oid = new OrderId();
           // oid.setCheckId(check.getId());
            order.getId().setCheckId(check.getId());

            if(!java.util.Objects.isNull(dish)) {
                order.getId().setDishId(dish.getId());
                //oid.setDishId(dish.getId());
                order.setDish(dish);
            }
            else throw new RuntimeException("Order controller must have a dish associated");

            //order.setId(oid);


            order.setQuantity((Integer)orderCreationItem.getQuantitySpinner().getValue());

            order.setCheck(check);
            OrderDAO.getInstance().insert(order);

            check.getOrders().add(order);
            CheckDAO.getInstance().update(check);


        }

        public void setDish(Dish dish){this.dish = dish;}
    }
}
