package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.RoomEditDeletionToolController;
import edu.unifi.model.entities.Room;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RoomUpdateTool extends RoomCreationTool {

    private Room room;
    private static volatile RoomUpdateTool instance;

    private RoomUpdateTool(String title, Room room, int width, int height) throws Exception {

        super(title,width,height);
        this.room = room;

        JButton updateButton = getCreateButton();
        updateButton.setText("Update");
        updateButton.setIcon(FontIcon.of(MaterialDesignP.PENCIL, 20));

        //remove all the actionListeners of the create button passed to the updateButton
        ActionListener[] actionListeners = updateButton.getActionListeners();
        for (ActionListener a : actionListeners)
            updateButton.removeActionListener(a);

        getTitleLabel().setText("Room Update Tool");
        getNameTextField().setText(room.getName());

        Notifier notifier = Notifier.getInstance();

        RoomEditDeletionToolController.RoomEditController roomEditController = new RoomEditDeletionToolController.RoomEditController(room,this);

        roomEditController.addObserver(notifier);
        updateButton.addActionListener(roomEditController);
    }

    public static RoomUpdateTool getInstance(String title, Room room, int width, int height) throws Exception {
        RoomUpdateTool thisInstance = instance;
        if (instance == null) {
            synchronized (RoomUpdateTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new RoomUpdateTool(title, room, width, height);
            }
        }
        return thisInstance;
    }

    //to "reset" the singleton
    @Override
    public void dispose() {
        instance = null;
        super.dispose();
    }

    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }
}
