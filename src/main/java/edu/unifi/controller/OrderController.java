package edu.unifi.controller;

import edu.unifi.model.entities.*;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.model.orm.dao.OrderDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.view.OrderCreationItem;
import edu.unifi.view.TableUpdateTool;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class OrderController {

    private static List<Dish> dishes;
    private static Check check;

    private static Table commonTable;

    private static TableUpdateTool tableUpdateTool;

    public List<Dish> getFilteredDishes(String filter) {
        dishes = DishDAO.getInstance().getAll();
        return dishes.stream().filter(dish -> dish.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class OrderCreationController implements ActionListener {
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

            check = CheckDAO.getInstance().getValideCheckByTable(commonTable);
            if(java.util.Objects.isNull(check)){

                //TODO:uniform with others and passing through notifier?
                JOptionPane.showMessageDialog(null,"You must create a new check", "Error!",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            dish = DishDAO.getInstance().getById(dish.getId());

            OrderId oid = new OrderId(check, dish);
            Order order = new Order(oid);

            order.setQuantity((Integer) orderCreationItem.getQuantitySpinner().getValue());

            java.util.List<Order> tableOrders = OrderDAO.getInstance().getAllTableOrders(commonTable,check);

            boolean contained = false;

            for(var a : tableOrders){
                if(Objects.equals(a.getId().getDish().getId(), dish.getId())){
                    order.setQuantity( ((Integer) orderCreationItem.getQuantitySpinner().getValue()).intValue() + a.getQuantity());
                    contained = true;
                }
            }
            if(contained){
                OrderDAO.getInstance().update(order);
            }else{
                OrderDAO.getInstance().insert(order);
            }
            tableUpdateTool.buildOrdersList(commonTable);

        }

        public void setDish(Dish dish) {
            this.dish = dish;
        }
    }

    public static class CheckCreationController implements ActionListener{

        public CheckCreationController(Table table){
            commonTable = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(!java.util.Objects.isNull(CheckDAO.getInstance().getValideCheckByTable(commonTable))) {

                //TODO:uniform with others and passing through notifier?
                JOptionPane.showMessageDialog(null,"This table already has an associated check.\n " +
                        "You can reset it using the decicated button", "Attention!",JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            commonTable = TableDAO.getInstance().getById(commonTable.getId());
            check = new Check();
            check.setIssueDate(java.time.LocalDateTime.now());
            check.setTable(commonTable);
            commonTable.getChecks().add(check);
            CheckDAO.getInstance().insert(check);

            //TODO:uniform with others and passing through notifier?
            JOptionPane.showMessageDialog(null,"New check created successfully", "Attention!",JOptionPane.INFORMATION_MESSAGE);


        }
    }

    public static class OrderEditController implements ActionListener{

        private OrderId oid;


        public OrderEditController(OrderId oid, TableUpdateTool tableUpdateToolt, Table table){
            this.oid = oid;
            commonTable = table;
            tableUpdateTool = tableUpdateToolt;
        }

        @Override
        public void actionPerformed(ActionEvent e){

            SpinnerNumberModel sModel = new SpinnerNumberModel(OrderDAO.getInstance().getById(oid).getQuantity(),0,1000,1);
            JSpinner spinner = new JSpinner(sModel);
            int option = JOptionPane.showOptionDialog(null, spinner, "Enter the new quantity",
                    JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);


            if (option == JOptionPane.OK_OPTION) {
                Order order = OrderDAO.getInstance().getById(oid);
                order.setQuantity(Integer.parseInt(spinner.getValue().toString()));
                OrderDAO.getInstance().update(order);
                tableUpdateTool.buildOrdersList(commonTable);
            }

        }

    }

    public static class OrderDeletionController implements ActionListener{

        private OrderId oid;

        public OrderDeletionController(OrderId oid, TableUpdateTool tableUpdateToolt, Table table){
            this.oid = oid;
            commonTable = table;
            tableUpdateTool = tableUpdateToolt;
        }

        @Override
        public void actionPerformed(ActionEvent e){

            Order order = OrderDAO.getInstance().getById(oid);
            OrderDAO.getInstance().delete(order);
            tableUpdateTool.buildOrdersList(commonTable);

        }
    }
}
