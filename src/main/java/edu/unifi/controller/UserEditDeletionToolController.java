package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.User;
import edu.unifi.model.orm.dao.UserDAO;
import edu.unifi.model.util.security.CurrentSession;
import edu.unifi.model.util.security.PasswordManager;
import edu.unifi.view.UserUpdateTool;
import org.apache.commons.validator.routines.EmailValidator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class UserEditDeletionToolController {

    private static List<User> users;

    public List<User> getFilteredUsers(String filter) {
        users = UserDAO.getInstance().getAll();
        return users.stream().filter(user -> user.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class UserEditController extends Observable implements ActionListener {
        private User user;
        private final UserUpdateTool userUpdateTool;

        public UserEditController(User user, UserUpdateTool userUpdateTool) {
            this.user = user;
            this.userUpdateTool = userUpdateTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String userName = userUpdateTool.getNameTextField().getText();
            String userSurname = userUpdateTool.getSurnameTextField().getText();
            String userEmail = userUpdateTool.getEmailTextField().getText();
            String userUsername = userUpdateTool.getUsernameTextField().getText();
            String userPassword = userUpdateTool.getPasswordTextField().getText();

            if (userName.isEmpty()) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "User name cannot be empty"));
                return;
            }
            if (userSurname.isEmpty()) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "User surname cannot be empty"));
                return;
            }
            if (!EmailValidator.getInstance().isValid(userEmail)) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Invalid email by RFC 822 Standards"));
                return;
            }
            //Check if the username inserted is empty or already used by someone else
            if (userUsername.isEmpty() || !Objects.equals(UserDAO.getInstance().getById(user.getId()).getId().toString(), UserDAO.getInstance().getByUsername(userUsername).getId().toString())) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Username is empty or already taken"));
                return;
            }
            if (userPassword.isEmpty()) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "User password cannot be empty"));
                return;
            }

            user = UserDAO.getInstance().getById(user.getId());
            user.setName(userName);
            user.setSurname(userSurname);
            user.setEmail(userEmail);
            user.setUsername(userUsername);
            user.setPasswordHash(PasswordManager.hash(userPassword.toCharArray()));
            UserDAO.getInstance().update(user);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.UPDATE_USER, user.getName() + " updated successfully"));
            userUpdateTool.dispose();
        }
    }

    public static class UserDeletionController extends Observable implements ActionListener {
        private User user;

        public UserDeletionController(User user) {
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(CurrentSession.getInstance().getUser().getId());
            System.out.println(user.getId());
            if (Objects.equals(CurrentSession.getInstance().getUser().getId().toString(), user.getId().toString())) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Cannot delete the actual user"));
                return;
            }

            users.remove(user);
            if (users.isEmpty())
                users = null;
            user = UserDAO.getInstance().getById(user.getId());
            UserDAO.getInstance().delete(user);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.DELETE_USER, user.getName() + " deleted successfully"));
        }

    }
    public void setUsersToNull() { users = null; }

}
