package edu.unifi.controller;

import edu.unifi.model.entities.User;
import edu.unifi.model.orm.dao.UserDAO;
import edu.unifi.model.util.security.PasswordManager;
import edu.unifi.view.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public record LoginController(Login login) implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        UserDAO userDAO = UserDAO.getInstance();
        User u = userDAO.getByUsername(login.getUsernameField().getText());

        if (u == null) {
            JOptionPane.showMessageDialog(null, "Username or password is not correct!");
            return;
        }

        if (PasswordManager.authenticate(login.getPasswordField().getPassword(), u.getPasswordHash())) {
            login.dispose();
            login.getLoginLatch().countDown();
            return;
        }
        JOptionPane.showMessageDialog(null, "Username or password is not correct!");
    }
}
