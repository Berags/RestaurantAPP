package edu.unifi;

import edu.unifi.controller.MessageType;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.view.DishCreationTool;
import edu.unifi.view.Home;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

import static edu.unifi.controller.MessageType.*;

public class Notifier implements Observer {
    private volatile static Notifier instance = null;
    private Home home;

    public static Notifier getInstance() throws Exception {
        Notifier thisInstance = instance;
        if (instance == null) {
            synchronized (DishCreationTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new Notifier();
            }
        }
        return thisInstance;
    }

    @Override
    public void update(Observable o, Object toDisplay) {
        switch ((MessageType) toDisplay) {
            case ADD_TABLE -> {
                home.showResultDialog("Table added successfully", true);
                home.showTables();
            }
            case DELETE_TABLE -> {
                home.showResultDialog("Table deleted successfully", true);
                home.showTables();
            }
            case UPDATE_TABLE -> {
                home.showResultDialog("Table updated successfully", true);
                home.showTables();
            }
            case ADD_DISH -> {
                home.showResultDialog("Dish added successfully", true);
            }
            case DELETE_DISH -> {
                home.showResultDialog("Dish deleted successfully", true);
            }
            case UPDATE_DISH -> {
                home.showResultDialog("Dish updated successfully", true);
            }
            case ADD_ROOM -> {
                home.showResultDialog("Room added successfully", true);
            }
            case DELETE_ROOM -> {
                home.showResultDialog("Room deleted successfully", true);
            }
            case UPDATE_ROOM -> {
                home.showResultDialog("Room updated successfully", true);
            }
            case ERROR -> {
                home.showResultDialog("An error occurred", false);
            }
            default -> throw new IllegalStateException("Unexpected value: " + toDisplay);
        }
    }

    public void setHome(Home home) {
        this.home = home;
    }
}
