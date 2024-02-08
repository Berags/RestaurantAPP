package edu.unifi.controller;

import edu.unifi.Notifier;
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
