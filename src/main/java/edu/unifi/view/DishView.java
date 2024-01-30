package edu.unifi.view;

import edu.unifi.controller.DishController;
import edu.unifi.model.entities.Dish;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class DishView extends Window {
    private JButton addButton;
    private JTextField searchTextField;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JLabel actionLabel;
    private final DishController dishController;
    private final JScrollPane listScroller = new JScrollPane();
    private JPanel panel2;
    private JPanel listPanel;

    public DishView(DishController dishController) throws Exception {
        super("Dishes", false, DISPOSE_ON_CLOSE, 0, 0, 600, 600);
        this.dishController = dishController;

        setupUi();
        buildList();
        listScroller.setViewportView(listPanel);
        panel2.add(listScroller, BorderLayout.CENTER);

        setVisible(true);
    }

    private void setupUi() throws Exception {
        setRootLayout(Layout.BORDER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        addComponent(panel1, BorderLayout.NORTH);
        addButton = new JButton();
        addButton.setText("add");
        addButton.setIcon(FontIcon.of(MaterialDesignP.PLUS_BOX, 20));
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
        idLabel = new JLabel();
        idLabel.setText("Id");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(idLabel, gbc);
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
        nameLabel = new JLabel();
        nameLabel.setText("Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(nameLabel, gbc);
        actionLabel = new JLabel();
        actionLabel.setText("Action");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(actionLabel, gbc);
        typeLabel = new JLabel();
        typeLabel.setText("Type");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(typeLabel, gbc);
    }

    private void buildList() {
        java.util.List<Dish> filteredDishes = dishController.getFilteredDishes(searchTextField.getText() == null ? "" : searchTextField.getText());
        listPanel = new JPanel(new GridLayout(filteredDishes.size(), 1));
        int index = 0;

        for (var d : filteredDishes) {
            GridBagConstraints gbc;
            JPanel listPanel = new JPanel();
            GridBagLayout layout = new GridBagLayout();
            listPanel.setLayout(layout);
            JLabel dishIdLabel = new JLabel();
            dishIdLabel.setText(d.getId().toString());
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.2;
            gbc.fill = GridBagConstraints.BOTH;
            listPanel.add(dishIdLabel, gbc);
            final JPanel spacer5 = new JPanel();
            gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            listPanel.add(spacer5, gbc);
            final JPanel spacer6 = new JPanel();
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.VERTICAL;
            listPanel.add(spacer6, gbc);
            JLabel dishNameLabel = new JLabel();
            dishNameLabel.setText(d.getName());
            gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.weightx = 0.8;
            gbc.anchor = GridBagConstraints.WEST;
            listPanel.add(dishNameLabel, gbc);
            JLabel dishTypeLabel = new JLabel();
            dishTypeLabel.setText(d.getTypeOfCourse().getName());
            gbc = new GridBagConstraints();
            gbc.gridx = 3;
            gbc.gridy = 0;
            gbc.weightx = 0.6;
            gbc.anchor = GridBagConstraints.WEST;
            listPanel.add(dishTypeLabel, gbc);
            JPanel actionTestPanel = new JPanel();
            actionTestPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            gbc = new GridBagConstraints();
            gbc.gridx = 4;
            gbc.gridy = 0;
            gbc.weightx = 0.2;
            gbc.fill = GridBagConstraints.BOTH;
            listPanel.add(actionTestPanel, gbc);
            JButton editButton = new JButton();
            editButton.setHideActionText(false);
            editButton.setHorizontalAlignment(0);
            editButton.setHorizontalTextPosition(0);
            editButton.setIcon(FontIcon.of(MaterialDesignP.PENCIL, 20));
            editButton.addActionListener(e -> {
                try {
                    new DishUpdateTool(d).setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            actionTestPanel.add(editButton);
            JButton deleteButton = new JButton();
            deleteButton.setHideActionText(false);
            deleteButton.setHorizontalAlignment(0);
            deleteButton.setHorizontalTextPosition(0);
            deleteButton.setIcon(FontIcon.of(MaterialDesignD.DELETE, 20));
            actionTestPanel.add(deleteButton);

            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = index++;
            gbc.weighty = 0.1;
            listPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
            this.listPanel.add(listPanel);
        }
    }
}