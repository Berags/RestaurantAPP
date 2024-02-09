package edu.unifi;

import edu.unifi.controller.MessageType;
import edu.unifi.view.*;
import java.util.Observable;
import java.util.Observer;

public class Notifier implements Observer {
    private volatile static Notifier instance = null;
    private Home home;
    private Login login;


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
                if (!RoomView.isDisposed())
                    RoomView.getInstance().updateList();
                home.updateRoom();
            }
            case ADD_DISH -> {
                home.showResultDialog(message.getStringMessage(), true);
                //TODO: To refactor the if statement
                if (!DishView.isDisposed()) {
                    DishView.getInstance().buildList();
                }
            }
            case DELETE_DISH -> {
                home.showResultDialog("Dish deleted successfully", true);
                DishView.getInstance().buildList();
            }
            case UPDATE_DISH -> {
                home.showResultDialog(message.getStringMessage(), true);
                DishView.getInstance().buildList();
            }
            case ADD_ROOM -> {
                home.showResultDialog("Room added successfully", true);
                home.updateHomeRooms();
                if (!RoomView.isDisposed())
                    RoomView.getInstance().updateList();
            }
            case DELETE_ROOM -> {
                home.showResultDialog("Room deleted successfully", true);
                home.updateHomeRooms();
                RoomView.getInstance().updateList();
            }
            case UPDATE_ROOM -> {
                home.showResultDialog("Room updated successfully", true);
                home.updateHomeRooms();
                RoomView.getInstance().updateList();
            }
            case CLEAN_CHECK -> {
                home.showResultDialog("Check cleaned successfully", true);
            }
            case ERROR -> {
                home.showResultDialog(message.getStringMessage(), false);
            }
            case EXIT -> {
                home.dispose();
                Main.notifyExit();
            }
            case WRONG_CREDENTIALS -> {
                login.showResultDialog("Username or password is not correct!");
            }
            default -> throw new IllegalStateException("Unexpected value: " + toDisplay);
        }
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

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
