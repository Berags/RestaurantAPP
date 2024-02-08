package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.Room;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.view.RoomCreationTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class RoomCreationToolController extends Observable implements ActionListener {

    private final RoomCreationTool roomCreationTool;

    public RoomCreationToolController(RoomCreationTool roomCreationTool) {
        this.roomCreationTool = roomCreationTool;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Room room = new Room("");
        String roomName = roomCreationTool.getNameTextField().getText();
        if (roomName.isEmpty() || RoomDAO.getInstance().getByName(roomName) != null) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "Invalid room name"));
            return;
        }

        room.setName(roomName);
        RoomDAO.getInstance().insert(room);

        setChanged();
        notifyObservers(Notifier.Message.build(MessageType.ADD_ROOM, roomName + " successfully added!"));
        roomCreationTool.dispose();
    }

}
