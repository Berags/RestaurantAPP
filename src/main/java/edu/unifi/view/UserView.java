package edu.unifi.view;

import edu.unifi.controller.UserEditDeletionToolController;
import edu.unifi.model.entities.User;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class UserView extends Window {

    private JButton addButton;
    private JTextField searchTextField;
    private JLabel nameSurnameLabel;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel roleLabel;
    private JLabel actionLabel;
    protected final JScrollPane listScroller = new JScrollPane();

    private JPanel panel1;
    private JPanel panel2;
    private JPanel listPanel;
    private static UserView instance;

    private java.util.List<User> filteredUsers = new ArrayList<>();

    /**
     * To have all the userItems, complete with buildList()
     * @param title
     * @throws Exception
     */
    protected UserView(String title) {
        super(title, false, DISPOSE_ON_CLOSE, 0, 0, 600, 600);
        try {
            setupUI();
        }catch(Exception e) {}
        setVisible(true);
    }


    private void setupUI() throws Exception {

        setRootLayout(Window.Layout.BORDER);
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        addComponent(panel1, BorderLayout.NORTH);
        addButton = new JButton();
        addButton.setText("add");
        addButton.setIcon(FontIcon.of(MaterialDesignP.PLUS_BOX, 20));

        addButton.addActionListener(e -> {
            try {
                UserCreationTool.getInstance("User creation tool",400,300);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 50;
        panel1.add(addButton, gbc);
        searchTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 50;
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buildList();
                listScroller.setViewportView(listPanel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buildList();
                listScroller.setViewportView(listPanel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buildList();
                listScroller.setViewportView(listPanel);
            }
        });

        panel1.add(searchTextField, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);

        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer2, gbc);

        panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        addComponent(panel2, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel2.add(panel3, BorderLayout.NORTH);

        nameSurnameLabel = new JLabel();
        nameSurnameLabel.setText("Name&Surname");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(nameSurnameLabel, gbc);

        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(spacer3, gbc);

        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(spacer4, gbc);

        usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(usernameLabel, gbc);

        emailLabel = new JLabel();
        emailLabel.setText("Email");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(emailLabel, gbc);

        roleLabel = new JLabel();
        roleLabel.setText("Role");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(roleLabel, gbc);

        actionLabel = new JLabel();
        actionLabel.setText("Action");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(actionLabel, gbc);
    }

    public static UserView getInstance() {
        UserView thisInstance = instance;
        if (instance == null) {
            synchronized (UserView.class) {
                if (thisInstance == null)
                    instance = thisInstance = new UserView("Users");
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

    public void buildList() {
        filteredUsers = new UserEditDeletionToolController().getFilteredUsers(searchTextField.getText() == null ? "" : searchTextField.getText());
        listPanel = new JPanel(new GridLayout(filteredUsers.size(), 1));
        int index = 0;

        for (var a : filteredUsers) {
            try {
                UserItem AI = new UserItem(a,index);
                this.listPanel.add(AI.getListPanel());
                index++;
            }catch (Exception e){}
        }
        listScroller.setViewportView(listPanel);
        panel2.add(listScroller, BorderLayout.CENTER);
    }

    public void updateList(){
        buildList();
        listScroller.setViewportView(listPanel);
    }

    public static boolean isDisposed() {
        return Objects.isNull(instance);
    }
}
