package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.User;
import edu.unifi.model.orm.dao.UserDAO;
import edu.unifi.model.util.security.CurrentSession;
import edu.unifi.model.util.security.PasswordManager;
import edu.unifi.model.util.security.Roles;
import edu.unifi.view.UserCreationTool;
import org.apache.commons.validator.routines.EmailValidator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Observable;

public class UserCreationToolController extends Observable implements ActionListener {

    private final UserCreationTool userCreationTool;

    public UserCreationToolController(UserCreationTool userCreationTool) {
        this.userCreationTool = userCreationTool;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = new User();

        if (userCreationTool.getNameTextField().getText().isEmpty()) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "User name cannot be empty"));
            return;
        }

        if (userCreationTool.getSurnameTextField().getText().isEmpty()) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "User surname cannot be empty"));
            return;
        }
        if (!EmailValidator.getInstance().isValid(userCreationTool.getEmailTextField().getText())){
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "Invalid email format by RFC 822 Standards"));
            return;
        }
        if (userCreationTool.getUsernameTextField().getText().isEmpty() || !java.util.Objects.isNull(UserDAO.getInstance().getByUsername(userCreationTool.getUsernameTextField().getText()))) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "Username epty or already taken"));
            return;
        }
        if (userCreationTool.getPasswordTextField().getText().isEmpty()) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "User password cannot be empty"));
            return;
        }
        user.setName(userCreationTool.getNameTextField().getText());
        user.setSurname(userCreationTool.getSurnameTextField().getText());
        user.setEmail(userCreationTool.getEmailTextField().getText());
        user.setUsername(userCreationTool.getUsernameTextField().getText());
        user.setPasswordHash(PasswordManager.hash(userCreationTool.getPasswordTextField().getText().toCharArray()));
        user.setRole((Roles) userCreationTool.getRoleComboBox().getSelectedItem());

        UserDAO.getInstance().insert(user);
        setChanged();
        notifyObservers(Notifier.Message.build(MessageType.ADD_USER, user.getUsername() + " successfully added!"));

        userCreationTool.dispose();
    }

}
