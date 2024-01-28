package edu.unifi.controller;

import edu.unifi.model.entities.Room;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.view.TableDeletionTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import edu.unifi.model.orm.dao.TableDAO;

public class TableDeletionToolController extends Observable implements ActionListener {
   private TableDeletionTool tableDeletionTool;
    public TableDeletionToolController(TableDeletionTool tableDeletionTool){
        this.tableDeletionTool = tableDeletionTool;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        TableDAO.getInstance().deleteById(tableDeletionTool.getSelectedTableId());
        setChanged();
        notifyObservers(MessageType.DELETE_TABLE);
        tableDeletionTool.dispose();
    }
}