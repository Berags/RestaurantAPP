package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.User;
import edu.unifi.model.orm.dao.UserDAO;
import edu.unifi.model.util.security.PasswordManager;
import edu.unifi.model.util.security.Roles;
import edu.unifi.model.util.security.aop.Authorize;
import edu.unifi.model.util.security.aop.AuthorizeAspect;
import edu.unifi.view.AccountCreationTool;
import org.apache.commons.validator.routines.EmailValidator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.regex.Pattern;

public class AccountCreationToolController extends Observable implements ActionListener {

    private final AccountCreationTool accountCreationTool;

    public AccountCreationToolController(AccountCreationTool accountCreationTool) {
        this.accountCreationTool = accountCreationTool;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = new User();

        if (accountCreationTool.getNameTextField().getText().isEmpty()) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "User name cannot be empty"));
            return;
        }

        if (accountCreationTool.getSurnameTextField().getText().isEmpty()) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "User surname cannot be empty"));
            return;
        }
        //FIXME: Fix the email validation that is incorrect
        if (EmailValidator.getInstance().isValid(accountCreationTool.getEmailTextField().getText())){
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "Invalid email format by RFC 822 Standards"));
            return;
        }
        if (accountCreationTool.getUsernameTextField().getText().isEmpty()) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "User username cannot be empty"));
            return;
        }
        if (accountCreationTool.getPasswordTextField().getText().isEmpty()) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ERROR, "User password cannot be empty"));
            return;
        }
        user.setName(accountCreationTool.getNameTextField().getText());
        user.setSurname(accountCreationTool.getSurnameTextField().getText());
        user.setEmail(accountCreationTool.getEmailTextField().getText());
        user.setUsername(accountCreationTool.getUsernameTextField().getText());
        user.setPasswordHash(PasswordManager.hash(accountCreationTool.getPasswordTextField().getText().toCharArray()));
        user.setRole((Roles) accountCreationTool.getRoleComboBox().getSelectedItem());

        UserDAO.getInstance().insert(user);
        setChanged();
        notifyObservers(Notifier.Message.build(MessageType.ADD_USER, user.getUsername() + " successfully added!"));

        accountCreationTool.dispose();
    }

}
