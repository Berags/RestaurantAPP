package edu.unifi;

import edu.unifi.controller.MessageType;
import edu.unifi.view.DishCreationTool;
import edu.unifi.view.DishView;
import edu.unifi.view.Home;

import java.util.Observable;
import java.util.Observer;

public class Notifier implements Observer {
    private volatile static Notifier instance = null;
    private Home home;

    private DishView dishView;

    private Notifier() throws Exception {
        if (instance != null)
            throw new Exception("Singleton class");
    }

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
        Message message = (Message) toDisplay;
        switch (message.type) {
            case ADD_TABLE, UPDATE_TABLE, DELETE_TABLE -> {
                home.showResultDialog(message.getStringMessage(), true);
                home.updateRoom();
            }
            case ADD_DISH -> {
                home.showResultDialog(message.getStringMessage(), true);
            }
            case DELETE_DISH -> {
                home.showResultDialog("Dish deleted successfully", true);
                dishView.updateList();
                dishView.repaint();
            }
            case UPDATE_DISH -> {
                home.showResultDialog("Dish updated successfully", true);
                dishView.updateList();
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
                home.showResultDialog(message.getStringMessage(), false);
            }
            case EXIT -> {
                home.dispose();
                Main.notifyExit();
            }
            default -> throw new IllegalStateException("Unexpected value: " + toDisplay);
        }
    }

    public void setHome(Home home) {
        this.home = home;
    }
    public void setDishView(DishView dishView){this.dishView = dishView;}

    public record Message(MessageType type, Object message) {
        public static Message build(MessageType type, String message) {
            return new Message(type, message);
        }

        public String getStringMessage() {
            if (message instanceof String)
                return (String) message;
            return null;
        }
    }
}
