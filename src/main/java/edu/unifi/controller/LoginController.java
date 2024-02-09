package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.User;
import edu.unifi.model.orm.dao.UserDAO;
import edu.unifi.model.util.security.CurrentSession;
import edu.unifi.model.util.security.PasswordManager;
import edu.unifi.view.Login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public final class LoginController extends Observable implements ActionListener {
    private final Login login;

    public LoginController(Login login) {
        this.login = login;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserDAO userDAO = UserDAO.getInstance();
        User u = userDAO.getByUsername(login.getUsernameField().getText());

        if (u == null) {
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.WRONG_CREDENTIALS, "Username or password is not correct!"));
            return;
        }

        if (PasswordManager.authenticate(login.getPasswordField().getPassword(), u.getPasswordHash())) {
            CurrentSession.getInstance().login(u);
            login.dispose();
            login.getLoginLatch().countDown();
            return;
        }
        setChanged();
        notifyObservers(Notifier.Message.build(MessageType.WRONG_CREDENTIALS, "Username or password is not correct!"));
    }
}
