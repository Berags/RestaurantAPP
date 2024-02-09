package edu.unifi.view;


import edu.unifi.Notifier;
import edu.unifi.controller.UserEditDeletionToolController;
import edu.unifi.model.entities.User;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UserUpdateTool extends UserCreationTool {

    private User user;
    private static volatile UserUpdateTool instance;

    private UserUpdateTool(String title, User user, int width, int height) throws Exception {

        super(title,width,height);
        this.user = user;

        JButton updateButton = getCreateButton();
        updateButton.setText("Update");
        updateButton.setIcon(FontIcon.of(MaterialDesignP.PENCIL, 20));

        //remove all the actionListeners of the create button passed to the updateButton
        ActionListener[] actionListeners = updateButton.getActionListeners();
        for (ActionListener a : actionListeners)
            updateButton.removeActionListener(a);

        getTitleLabel().setText("User Update Tool");
        getNameTextField().setText(user.getName());
        getSurnameTextField().setText(user.getSurname());
        getEmailTextField().setText(user.getEmail());
        getUsernameTextField().setText(user.getUsername());
        getPasswordTextField().setText("");

        Notifier notifier = Notifier.getInstance();

        UserEditDeletionToolController.UserEditController userEditController = new UserEditDeletionToolController.UserEditController(user, this);

        userEditController.addObserver(notifier);
        updateButton.addActionListener(userEditController);
    }

    public static UserUpdateTool getInstance(String title, User user, int width, int height) throws Exception {
        UserUpdateTool thisInstance = instance;
        if (instance == null) {
            synchronized (UserUpdateTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new UserUpdateTool(title, user, width, height);
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

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }


}
