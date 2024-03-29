package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.DishToolController;
import edu.unifi.controller.DishToolController.DishCreationToolController;
import edu.unifi.model.entities.TypeOfCourse;
import edu.unifi.model.orm.dao.TypeOfCourseDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DishCreationTool extends Window {
    private JLabel titleLabel;
    private JTextField nameField;
    private JTextField priceTextField;
    private JTextArea descriptionTextArea;
    private JComboBox typeComboBox;
    private JButton createButton;
    private JPanel panel;
    private JLabel priceLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel typeLabel;
    private static volatile DishCreationTool instance = null;

    protected DishCreationTool(String title, int width, int height) throws Exception {
        super("Dish Creation Tool",false, JFrame.DISPOSE_ON_CLOSE, 0, 0, width, height);
        setUpUI();
        DishCreationToolController dishCreationToolController = new DishToolController.DishCreationToolController(this);
        dishCreationToolController.addObserver(Notifier.getInstance());
        createButton.addActionListener(dishCreationToolController);
        pack();
    }

    private void setUpUI() throws Exception {
        setRootLayout(Layout.BORDER);

        titleLabel = new JLabel("Dish Creation Tool");
        Font titleLabelFont = getFont(null, Font.BOLD, 22, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setHorizontalTextPosition(10);
        titleLabel.setText("Dish Creation Tool");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer1, gbc);
        priceLabel = new JLabel();
        Font priceLabelFont = getFont(null, Font.BOLD, 18, priceLabel.getFont());
        if (priceLabelFont != null) priceLabel.setFont(priceLabelFont);
        priceLabel.setText("Price");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 15, 0);
        panel.add(priceLabel, gbc);
        priceTextField = new JTextField();
        Font priceTextFieldFont = getFont(null, -1, 18, priceTextField.getFont());
        if (priceTextFieldFont != null) priceTextField.setFont(priceTextFieldFont);
        priceTextField.setText("0.00");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 50);
        panel.add(priceTextField, gbc);
        descriptionLabel = new JLabel();
        Font descriptionLabelFont = getFont(null, Font.BOLD, 18, descriptionLabel.getFont());
        if (descriptionLabelFont != null) descriptionLabel.setFont(descriptionLabelFont);
        descriptionLabel.setText("Description");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 15, 0);
        panel.add(descriptionLabel, gbc);
        nameLabel = new JLabel();
        Font nameLabelFont = getFont(null, Font.BOLD, 18, nameLabel.getFont());
        if (nameLabelFont != null) nameLabel.setFont(nameLabelFont);
        nameLabel.setText("Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 15, 0);
        panel.add(nameLabel, gbc);
        nameField = new JTextField();
        Font nameFieldFont = getFont(null, -1, 18, nameField.getFont());
        if (nameFieldFont != null) nameField.setFont(nameFieldFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 50);
        panel.add(nameField, gbc);
        descriptionTextArea = new JTextArea();
        Font descriptionTextAreaFont = getFont(null, -1, 18, descriptionTextArea.getFont());
        if (descriptionTextAreaFont != null) descriptionTextArea.setFont(descriptionTextAreaFont);
        descriptionTextArea.setRows(3);
        descriptionTextArea.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 15, 50);
        panel.add(descriptionTextArea, gbc);

        List<TypeOfCourse> listOfTypes = (TypeOfCourseDAO.getInstance().getAll());

        typeComboBox = new JComboBox<>(listOfTypes.toArray());
        Font typeComboBoxFont = getFont(null, -1, 18, typeComboBox.getFont());
        if (typeComboBoxFont != null) typeComboBox.setFont(typeComboBoxFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 50);
        panel.add(typeComboBox, gbc);
        typeLabel = new JLabel();
        Font typeLabelFont = getFont(null, Font.BOLD, 18, typeLabel.getFont());
        if (typeLabelFont != null) typeLabel.setFont(typeLabelFont);
        typeLabel.setText("Type");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 15, 0);
        panel.add(typeLabel, gbc);
        createButton = new JButton();
        Font createButtonFont = getFont(null, Font.BOLD, 18, createButton.getFont());
        if (createButtonFont != null) createButton.setFont(createButtonFont);
        createButton.setText("Create");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 30, 50);
        panel.add(createButton, gbc);
        priceLabel.setLabelFor(priceTextField);
        descriptionLabel.setLabelFor(descriptionTextArea);
        nameLabel.setLabelFor(nameField);
        typeLabel.setLabelFor(typeComboBox);

        createButton.setName("CreateDish");
        priceTextField.setName("PriceTextField");
        descriptionTextArea.setName("DescriptionTextArea");
        typeComboBox.setName("TypeComboBox");
        nameField.setName("NameField");

        addComponent(titleLabel, BorderLayout.NORTH);
        addComponent(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    //singleton
    public static DishCreationTool getInstance(String title, int width, int height) throws Exception {
        DishCreationTool thisInstance = instance;
        if (instance == null) {
            synchronized (DishCreationTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new DishCreationTool(title,width,height);
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

    public JTextField getNameTextField() {
        return nameField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public JTextArea getDescriptionLabel() {
        return descriptionTextArea;
    }

    public JComboBox getTypeComboBox() {
        return typeComboBox;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextArea getDescriptionTextArea() {
        return descriptionTextArea;
    }

    public JButton getCreateButton() {return createButton;}

    public static void setInstance(DishCreationTool instance) {
        DishCreationTool.instance = instance;
    }
}
