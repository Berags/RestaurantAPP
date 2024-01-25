package edu.unifi.view;

import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TableCreationTool extends Window {
    private JTextField nameTextField;
    private JSpinner nOfSeatsSpinner;
    private JComboBox stateComboBox;
    private JLabel nameLabel;
    private JLabel nOfSeatsLabel;
    private JButton createButton;
    private JLabel stateLabel;
    private JLabel titleLabel;
    private FontIcon createFontIcon;

    public TableCreationTool() throws Exception {
        super("Table Creation Tool", false, JFrame.DISPOSE_ON_CLOSE, 0, 0, 400, 300);
        setRootLayout(Layout.BORDER, 0, 0);
        titleLabel = new JLabel();
        Font titleLabelFont = getFont(null, Font.BOLD, 22, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setText("Table Creation Tool");
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
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
        panel.add(nameLabel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer1, gbc);
        nameTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 50);
        panel.add(nameTextField, gbc);
        nOfSeatsLabel = new JLabel();
        Font nOfSeatsLabelFont = getFont(null, -1, 18, nOfSeatsLabel.getFont());
        if (nOfSeatsLabelFont != null) nOfSeatsLabel.setFont(nOfSeatsLabelFont);
        nOfSeatsLabel.setText("Number of seats");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(15, 50, 0, 0);
        panel.add(nOfSeatsLabel, gbc);
        nOfSeatsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 0, 50);
        panel.add(nOfSeatsSpinner, gbc);
        stateComboBox = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 0, 50);
        panel.add(stateComboBox, gbc);
        stateLabel = new JLabel();
        Font stateLabelFont = getFont(null, -1, 18, stateLabel.getFont());
        if (stateLabelFont != null) stateLabel.setFont(stateLabelFont);
        stateLabel.setText("State");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(15, 50, 0, 0);
        panel.add(stateLabel, gbc);
        createButton = new JButton();
        Font createButtonFont = getFont(null, Font.BOLD, 18, createButton.getFont());
        if (createButtonFont != null) createButton.setFont(createButtonFont);
        createButton.setText("Create");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 50);
        panel.add(createButton, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer2, gbc);
        nameLabel.setLabelFor(nameTextField);
        nOfSeatsLabel.setLabelFor(nOfSeatsSpinner);
        stateLabel.setLabelFor(stateComboBox);

        createFontIcon = FontIcon.of(MaterialDesignP.PLUS_BOX_OUTLINE, 20);
        createButton.setIcon(createFontIcon);

        addComponent(titleLabel, BorderLayout.NORTH);
        addComponent(panel, BorderLayout.CENTER);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(JTextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public JSpinner getNOfSeatsSpinner() {
        return nOfSeatsSpinner;
    }

    public void setNOfSeatsSpinner(JSpinner nOfSeatsSpinner) {
        this.nOfSeatsSpinner = nOfSeatsSpinner;
    }

    public JComboBox getStateComboBox() {
        return stateComboBox;
    }

    public void setStateComboBox(JComboBox stateComboBox) {
        this.stateComboBox = stateComboBox;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JLabel getnOfSeatsLabel() {
        return nOfSeatsLabel;
    }

    public void setnOfSeatsLabel(JLabel nOfSeatsLabel) {
        this.nOfSeatsLabel = nOfSeatsLabel;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public void setCreateButton(JButton createButton) {
        this.createButton = createButton;
    }

    public JLabel getStateLabel() {
        return stateLabel;
    }

    public void setStateLabel(JLabel stateLabel) {
        this.stateLabel = stateLabel;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }
}
