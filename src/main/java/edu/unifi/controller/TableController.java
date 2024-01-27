package edu.unifi.controller;

import edu.unifi.model.entities.Table;
import edu.unifi.model.entities.TableState;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.view.TableUpdateTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public record TableController(Table table, TableUpdateTool tableUpdateTool) implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean ok = true;
        System.out.println("Table " + table.getName() + " updated");
        table.setName(tableUpdateTool.getNameTextField().getText());
        table.setNOfSeats((Integer) tableUpdateTool.getNOfSeatsSpinner().getValue());
        table.setState((TableState) tableUpdateTool.getStateComboBox().getSelectedItem());
        table.setRoom(tableUpdateTool.getRoom());
        try {
            TableDAO.getInstance().update(table);
        } catch (Exception ex) {
            ok = false;
        }
        tableUpdateTool.showResultDialog(ok ? "Table updated successfully" : "Error while updating table", ok);
        // TODO: notify Home to update the table
    }
}
