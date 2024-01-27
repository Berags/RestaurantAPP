package edu.unifi.controller;

import edu.unifi.view.TableDeletionTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.unifi.model.orm.dao.TableDAO;

public record TableDeletionToolController(TableDeletionTool tableDeletionTool) implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        TableDAO.getInstance().deleteById(tableDeletionTool.getSelectedTableId());
        tableDeletionTool.dispose();
    }
}
