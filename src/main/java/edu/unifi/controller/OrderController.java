package edu.unifi.controller;

import edu.unifi.Notifier;
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
import java.util.Observable;

public class OrderController {

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
            if(java.util.Objects.isNull(check)){

                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "You must create a new check"));
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

    public static class CheckCreationController extends Observable implements ActionListener{

        public CheckCreationController(Table table){
            commonTable = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(!java.util.Objects.isNull(CheckDAO.getInstance().getValidCheckByTable(commonTable))) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "This table already has an associated check.\n " +
                        "You can clean it using the decicated button"));
                return;
            }

            commonTable = TableDAO.getInstance().getById(commonTable.getId());
            check = new Check();
            //we set the issue date with the creation date, then we update it during the printing of the check
            check.setIssueDate(java.time.LocalDateTime.now());
            check.setTable(commonTable);
            commonTable.getChecks().add(check);
            CheckDAO.getInstance().insert(check);

            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ADD_DISH, "New check created successfully"));

        }
    }
    public static class CheckResetController extends Observable implements ActionListener{

        private TableUpdateTool tableUpdateTool;
        private Table table;

        public CheckResetController(TableUpdateTool tableUpdateTool, Table table){

            this.tableUpdateTool = tableUpdateTool;
            this.table = table;

        }

        @Override
        public void actionPerformed(ActionEvent e){

            if(java.util.Objects.isNull(CheckDAO.getInstance().getValidCheckByTable(table))){
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "There is any check to delete!"));
                return;
            }
            //to delete all the orders present in the check
            for (var a:tableUpdateTool.getOrders())
                OrderDAO.getInstance().delete(a);


            Check check1 = CheckDAO.getInstance().getValidCheckByTable(table);

            table.getChecks().removeAll(table.getChecks());

            CheckDAO.getInstance().delete(check1);


            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.CLEAN_CHECK, "Orders and check deleted successfully"));
            tableUpdateTool.buildOrdersList(table);
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
