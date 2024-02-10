package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.*;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.OrderDAO;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.view.TableCreationTool;
import edu.unifi.view.TableDeletionTool;
import edu.unifi.view.TableUpdateTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class TableToolController {

    public static class TableCreationToolController extends Observable implements ActionListener {
        private final TableCreationTool tableCreationTool;

        public TableCreationToolController(TableCreationTool tableCreationTool) {
            this.tableCreationTool = tableCreationTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Table table = new Table();
            String tableName = tableCreationTool.getNameTextField().getText();

            if (tableName.isEmpty()) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Table name cannot be empty"));
                return;
            }

            table.setName(tableName);
            table.setNOfSeats((Integer) tableCreationTool.getNOfSeatsSpinner().getValue());
            table.setState((TableState) tableCreationTool.getStateComboBox().getSelectedItem());
            Room room = RoomDAO.getInstance().getByName((String) tableCreationTool.getRoomComboBox().getSelectedItem());
            table.setRoom(room);

            TableDAO.getInstance().insert(table);

            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ADD_TABLE, tableName + " successfully added!"));
            tableCreationTool.dispose();
        }
    }

    public static class TableUpdateToolController extends Observable implements ActionListener {
        private final Table table;
        private final TableUpdateTool tableUpdateTool;

        private java.util.List<Order> orders;

        public TableUpdateToolController(Table table, TableUpdateTool tableUpdateTool) {
            this.table = table;
            this.tableUpdateTool = tableUpdateTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String newTableName = tableUpdateTool.getNameTextField().getText();

            //to check if the name field is empty
            if (newTableName.isEmpty()) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Table name cannot be empty"));
                return;
            }

            //we update the table with the information inserted
            table.setName(newTableName);
            table.setNOfSeats((Integer) tableUpdateTool.getNOfSeatsSpinner().getValue());
            table.setState((TableState) tableUpdateTool.getStateComboBox().getSelectedItem());
            table.setRoom(tableUpdateTool.getRoom());
            try {
                TableDAO.getInstance().update(table);
            } catch (Exception ex) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Error in the compilation"));
                return;
            }

            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.UPDATE_TABLE, table.getName() + " successfully updated!"));
        }

        /**
         *
         * @param table
         * @return the orders associated with the open check of the table
         */
        public java.util.List<Order> getTableOrders(Table table){

            Check check = CheckDAO.getInstance().getValidCheckByTable(table);
            table.getChecks().add(check);

            if(!java.util.Objects.isNull(check))
                return OrderDAO.getInstance().getAllTableOrders(table, check);
            return new ArrayList<>();

        }
    }

    public static class TableDeletionToolController extends Observable implements ActionListener {
        private TableDeletionTool tableDeletionTool;

        public TableDeletionToolController(TableDeletionTool tableDeletionTool) {
            this.tableDeletionTool = tableDeletionTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            TableDAO.getInstance().deleteById(tableDeletionTool.getSelectedTableId());
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.DELETE_TABLE, "Table successfully deleted!"));
            tableDeletionTool.dispose();
        }
        public List<Room> getRooms() {
            return RoomDAO.getInstance().getAll();
        }

        public Room getRoomByName(String name) {
            return RoomDAO.getInstance().getByName(name);
        }
    }
}
