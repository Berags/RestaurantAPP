package edu.unifi.controller;
import edu.unifi.Notifier;
import edu.unifi.model.entities.Room;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.view.RoomUpdateTool;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

public class RoomEditDeletionToolController {
    private static List<Room> rooms;

    public List<Room> getFilteredRooms(String filter) {
        if (rooms == null || rooms.isEmpty())
            rooms = RoomDAO.getInstance().getAll();
        if (filter == null || filter.isEmpty())
            return rooms;
        return rooms.stream().filter(room -> room.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class RoomEditController extends Observable implements ActionListener {
        private final Room room;
        private final RoomUpdateTool roomUpdateTool;

        public RoomEditController(Room room, RoomUpdateTool roomUpdateTool) {
            this.room = room;
            this.roomUpdateTool = roomUpdateTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String roomName = roomUpdateTool.getNameTextField().getText();
            if (roomName.isEmpty() || RoomDAO.getInstance().getById(roomName) != null) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Invalid room name"));
                return;
            }
            room.setName(roomName);
            RoomDAO.getInstance().update(room);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.UPDATE_ROOM, room.getName() + " updated successfully"));
            roomUpdateTool.dispose();
        }
    }

    public static class RoomDeletionController extends Observable implements ActionListener {
        private final Room room;

        public RoomDeletionController(Room room) {
            this.room = room;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rooms.remove(room);
            if (rooms.isEmpty())
                rooms = null;
            RoomDAO.getInstance().delete(room);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.DELETE_ROOM, room.getName() + " deleted successfully"));
        }

    }
    public void setRoomsToNull() { rooms = null; }

}
