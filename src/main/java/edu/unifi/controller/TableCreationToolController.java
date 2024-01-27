package edu.unifi.controller;

import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.Table;
import edu.unifi.model.entities.TableState;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.view.TableCreationTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class TableCreationToolController extends Observable implements ActionListener {
    private final TableCreationTool tableCreationTool;

    public TableCreationToolController(TableCreationTool tableCreationTool) {
        this.tableCreationTool = tableCreationTool;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Table table = new Table();
        table.setName(tableCreationTool.getNameTextField().getText());
        table.setNOfSeats((Integer) tableCreationTool.getNOfSeatsSpinner().getValue());
        table.setState((TableState) tableCreationTool.getStateComboBox().getSelectedItem());
        Room room = RoomDAO.getInstance().getById((String) tableCreationTool.getRoomComboBox().getSelectedItem());
        table.setRoom(room);

        TableDAO.getInstance().insert(table);

        setChanged();
        notifyObservers(MessageType.ADD_TABLE);
        tableCreationTool.dispose();
    }
}
