package edu.unifi.view;

import edu.unifi.controller.LoginController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class Login extends Window {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel titleLabel;
    private final JPanel loginPane;
    private final JLabel passwordLabel;
    private final JLabel usernameLabel;
    private final Font font = new Font("Droid Sans Mono Slashed", -1, 18);
    private final CountDownLatch loginLatch = new CountDownLatch(1);


    public Login() throws Exception {
        super("Login", false, JFrame.EXIT_ON_CLOSE, 0, 0, 400, 300);

        setRootLayout(Layout.BORDER, 50, 20);
        titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setText("Login - RestaurantAPP");
        addComponent(titleLabel, BorderLayout.NORTH);
        loginPane = new JPanel();
        loginPane.setLayout(new GridBagLayout());
        usernameField = new JTextField();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPane.add(usernameField, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPane.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        loginPane.add(spacer2, gbc);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 24));
        passwordField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPane.add(passwordField, gbc);
        passwordLabel = new JLabel();
        passwordLabel.setText("Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        loginPane.add(passwordLabel, gbc);
        usernameLabel = new JLabel();
        usernameLabel.setBorder(new EmptyBorder(0, 0, 0, 50));
        usernameLabel.setText("Username");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        loginPane.add(usernameLabel, gbc);
        loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.addActionListener(new LoginController(this));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPane.add(loginButton, gbc);

        titleLabel.setFont(font);
        usernameLabel.setFont(font);
        usernameField.setFont(font);
        passwordLabel.setFont(font);
        passwordField.setFont(font);

        addComponent(loginPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public CountDownLatch getLoginLatch() {
        return loginLatch;
    }
}
