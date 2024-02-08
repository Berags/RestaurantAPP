package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.Check;
import edu.unifi.model.entities.Order;
import edu.unifi.model.entities.Table;
import edu.unifi.model.entities.TableState;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.OrderDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.view.TableUpdateTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

public final class TableUpdateController extends Observable implements ActionListener {
    private final Table table;
    private final TableUpdateTool tableUpdateTool;

    private java.util.List<Order> orders;

    public TableUpdateController(Table table, TableUpdateTool tableUpdateTool) {
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