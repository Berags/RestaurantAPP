package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.AccountCreationToolController;
import edu.unifi.model.util.security.Roles;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AccountCreationTool extends Window {

    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel surnameLabel;
    private JTextField surnameTextField;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordTextField;
    private JLabel roleLabel;
    protected JButton createButton;
    private JLabel titleLabel;
    private FontIcon createFontIcon;
    private JComboBox roleComboBox;
    private JPanel gridPanel;

    private static volatile AccountCreationTool instance = null;

    protected AccountCreationTool(String title, int width, int height) throws Exception {
        super(title, false, JFrame.DISPOSE_ON_CLOSE, 0, 0, width, height);
        setUpUI();

        AccountCreationToolController accountCreationToolController = new AccountCreationToolController(this);
        accountCreationToolController.addObserver(Notifier.getInstance());

        setVisible(true);
    }

    private void setUpUI() throws Exception {

        gridPanel = rootPane;
        gridPanel.setLayout(new GridBagLayout());

        titleLabel = new JLabel();
        Font titleLabelFont = getFont(null, Font.BOLD, 22, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setText("Account Creation Tool");

        nameLabel = new JLabel();
        Font nameLabelFont = getFont(null, -1, 18, nameLabel.getFont());
        if (nameLabelFont != null) nameLabel.setFont(nameLabelFont);
        nameLabel.setText("Name");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 0, 0);
        gridPanel.add(nameLabel,gbc);

        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridPanel.add(spacer1, gbc);

        nameTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 50);
        gridPanel.add(nameTextField, gbc);

        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gridPanel.add(spacer7, gbc);


        surnameLabel = new JLabel();
        nameLabelFont = getFont(null, -1, 18, nameLabel.getFont());
        if (nameLabelFont != null) surnameLabel.setFont(nameLabelFont);
        surnameLabel.setText("Surname");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 0, 0);
        gridPanel.add(surnameLabel,gbc);

        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridPanel.add(spacer2, gbc);

        surnameTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 50);
        gridPanel.add(surnameTextField, gbc);

        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        gridPanel.add(spacer8, gbc);

        emailLabel = new JLabel();
        nameLabelFont = getFont(null, -1, 18, nameLabel.getFont());
        if (nameLabelFont != null) emailLabel.setFont(nameLabelFont);
        emailLabel.setText("Email");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 0, 0);
        gridPanel.add(emailLabel,gbc);

        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridPanel.add(spacer3, gbc);

        emailTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 50);
        gridPanel.add(emailTextField, gbc);

        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        gridPanel.add(spacer9, gbc);

        usernameLabel = new JLabel();
        nameLabelFont = getFont(null, -1, 18, nameLabel.getFont());
        if (nameLabelFont != null) usernameLabel.setFont(nameLabelFont);
        usernameLabel.setText("Username");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 0, 0);
        gridPanel.add(usernameLabel,gbc);

        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridPanel.add(spacer4, gbc);

        usernameTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 50);
        gridPanel.add(usernameTextField, gbc);

        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.VERTICAL;
        gridPanel.add(spacer10, gbc);

        passwordLabel = new JLabel();
        nameLabelFont = getFont(null, -1, 18, nameLabel.getFont());
        if (nameLabelFont != null) passwordLabel.setFont(nameLabelFont);
        passwordLabel.setText("Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 0, 0);
        gridPanel.add(passwordLabel,gbc);

        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridPanel.add(spacer5, gbc);

        passwordTextField = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 50);
        gridPanel.add(passwordTextField, gbc);

        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.VERTICAL;
        gridPanel.add(spacer11, gbc);

        roleLabel = new JLabel();
        nameLabelFont = getFont(null, -1, 18, nameLabel.getFont());
        if (nameLabelFont != null) roleLabel.setFont(nameLabelFont);
        roleLabel.setText("Role");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 0, 0);
        gridPanel.add(roleLabel,gbc);

        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gridPanel.add(spacer6, gbc);

        ArrayList<Roles> listOfRoles = new ArrayList<>(Arrays.asList(Roles.values()));
        roleComboBox = new JComboBox<>(listOfRoles.toArray());

        //for (var r : listOfRoles){
         //   roleComboBox.addItem(r.toString());
       // }

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 0, 50);
        gridPanel.add(roleComboBox, gbc);



        createButton = new JButton();
        Font createButtonFont = getFont(null, Font.BOLD, 18, createButton.getFont());
        if (createButtonFont != null) createButton.setFont(createButtonFont);
        createButton.setText("Create");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 11;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 50);
        AccountCreationToolController accountCreationToolController = new AccountCreationToolController(this);
        accountCreationToolController.addObserver(Notifier.getInstance());
        createButton.addActionListener(accountCreationToolController);
        gridPanel.add(createButton, gbc);


        nameTextField.setName("nameTextField");
        surnameTextField.setText("");
        emailTextField.setText("");
        usernameTextField.setText("");
        passwordTextField.setText("");

        createButton.setName("createButton");

        createFontIcon = FontIcon.of(MaterialDesignP.PLUS_BOX_OUTLINE, 20);

        createButton.setIcon(createFontIcon);

    }

    public static AccountCreationTool getInstance(String title, int width, int height) throws Exception {
        AccountCreationTool thisInstance = instance;
        if (instance == null) {
            synchronized (TableCreationTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new AccountCreationTool(title, width, height);
            }
        }
        return thisInstance;
    }
    @Override
    public void dispose() {
        instance = null;
        super.dispose();
    }

    public JTextField getNameTextField() { return nameTextField; }
    public JTextField getSurnameTextField() { return surnameTextField; }
    public JTextField getEmailTextField() { return emailTextField; }
    public JTextField getUsernameTextField() { return usernameTextField; }
    public JPasswordField getPasswordTextField() { return passwordTextField; }
    public JComboBox getRoleComboBox() { return roleComboBox; }
}
