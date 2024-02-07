package edu.unifi.controller;

import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.User;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.UserDAO;
import edu.unifi.view.Home;

import java.util.List;

public class HomeController {
    private final Home home;

    public HomeController(Home home) {
        this.home = home;
    }

    public List<Room> getRooms() {
        return RoomDAO.getInstance().getAll();
    }

    public Room getById(String name) {
        return RoomDAO.getInstance().getById(name);
    }

    public User getUserByUsername(String username) { return UserDAO.getInstance().getByUsername(username); }
}
