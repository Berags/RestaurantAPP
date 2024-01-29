package edu.unifi.controller;

import edu.unifi.model.entities.Table;
import edu.unifi.model.entities.TableState;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.view.TableUpdateTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Observable;

public final class TableController extends Observable implements ActionListener {
    private final Table table;
    private final TableUpdateTool tableUpdateTool;

    public TableController(Table table, TableUpdateTool tableUpdateTool) {
        this.table = table;
        this.tableUpdateTool = tableUpdateTool;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean ok = true;
        System.out.println("Table " + table.getName() + " updated");

        String newTableName = tableUpdateTool.getNameTextField().getText();

        if(!newTableName.equals("")) {
            table.setName(newTableName);
            table.setNOfSeats((Integer) tableUpdateTool.getNOfSeatsSpinner().getValue());
            table.setState((TableState) tableUpdateTool.getStateComboBox().getSelectedItem());
            table.setRoom(tableUpdateTool.getRoom());
            try {
                TableDAO.getInstance().update(table);
            } catch (Exception ex) {
                ok = false;
            }

            setChanged();
            notifyObservers(ok ? MessageType.UPDATE_TABLE : MessageType.ERROR);
        }else{
            //TODO: to uniform with other error messages?
            JOptionPane.showMessageDialog(null, "The table name must not be blank", "Error in the compilation", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Table table() {
        return table;
    }

    public TableUpdateTool tableUpdateTool() {
        return tableUpdateTool;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TableController) obj;
        return Objects.equals(this.table, that.table) &&
                Objects.equals(this.tableUpdateTool, that.tableUpdateTool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, tableUpdateTool);
    }

    @Override
    public String toString() {
        return "TableController[" +
                "table=" + table + ", " +
                "tableUpdateTool=" + tableUpdateTool + ']';
    }

}
