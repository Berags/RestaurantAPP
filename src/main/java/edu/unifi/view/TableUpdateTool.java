package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.OrderController;
import edu.unifi.controller.TableController;
import edu.unifi.model.entities.Table;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignU;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TableUpdateTool extends TableCreationTool {
    private JPanel rightPanel;
    private JButton addButton;
    private JButton printReceiptButton;
    private JFormattedTextField totalField;
    private JPanel labelPanel;
    private JLabel dishLabel;
    private JLabel quantityLabel;
    private JLabel totalLabel;
    private JLabel actionsLabel;
    private JPanel bottomPanel;
    private JLabel receiptTotalLabel;
    private Table table;
    private final TableController tableController;

    public TableUpdateTool(String title, Table table, int width, int height) throws Exception {
        super(title, width, height);
        this.table = table;
        setUpRightUI();

        ActionListener[] actionListeners = getCreateButton().getActionListeners();
        for (ActionListener a : actionListeners)
            getCreateButton().removeActionListener(a);

        getTitleLabel().setText("Table Update Tool");
        getNameTextField().setText(table.getName());
        getNOfSeatsSpinner().setValue(table.getNOfSeats());
        getStateComboBox().setSelectedItem(table.getState());
        getRoomComboBox().setSelectedItem(table.getRoom().getName());

        getCreateButton().setText("Update");
        getCreateButton().setIcon(FontIcon.of(MaterialDesignU.UPDATE, 20));

        tableController = new TableController(table, this);
        tableController.addObserver(Notifier.getInstance());
        getCreateButton().addActionListener(tableController);

        setVisible(true);
    }

    private void setUpRightUI() {

        JPanel leftPanel = getLeftPanel();
        JPanel gridPanel = getGridPanel();
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(0,2));

        gridPanel.add(leftPanel);

        rightPanel = new JPanel();

        rightPanel.setLayout(new BorderLayout(0, 0));
        Font rightPanelFont = getFont(null, Font.BOLD, 18, rightPanel.getFont());
        if (rightPanelFont != null) rightPanel.setFont(rightPanelFont);
        rightPanel.setForeground(new Color(-12875280));
        rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216))));
        labelPanel = new JPanel();
        labelPanel.setLayout(new GridBagLayout());
        rightPanel.add(labelPanel, BorderLayout.NORTH);
        labelPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-2104859)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        dishLabel = new JLabel();
        Font dishLabelFont = getFont(null, Font.BOLD, 18, dishLabel.getFont());
        if (dishLabelFont != null) dishLabel.setFont(dishLabelFont);
        dishLabel.setText("Dish");

        GridBagConstraints gbc;

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        labelPanel.add(dishLabel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        labelPanel.add(spacer1, gbc);
        quantityLabel = new JLabel();
        Font quantityLabelFont = getFont(null, Font.BOLD, 18, quantityLabel.getFont());
        if (quantityLabelFont != null) quantityLabel.setFont(quantityLabelFont);
        quantityLabel.setText("Quantity");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        labelPanel.add(quantityLabel, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        labelPanel.add(spacer2, gbc);
        totalLabel = new JLabel();
        Font totalLabelFont = getFont(null, Font.BOLD, 18, totalLabel.getFont());
        if (totalLabelFont != null) totalLabel.setFont(totalLabelFont);
        totalLabel.setText("Total");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        labelPanel.add(totalLabel, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        labelPanel.add(spacer3, gbc);
        actionsLabel = new JLabel();
        Font actionsLabelFont = getFont(null, Font.BOLD, 18, actionsLabel.getFont());
        if (actionsLabelFont != null) actionsLabel.setFont(actionsLabelFont);
        actionsLabel.setText("Actions");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        labelPanel.add(actionsLabel, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        labelPanel.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        labelPanel.add(spacer5, gbc);


        JPanel orderPanel = new JPanel();
        rightPanel.add(orderPanel, BorderLayout.CENTER);


        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        addButton = new JButton();
        addButton.setText("Add");
        addButton.setIcon(FontIcon.of(MaterialDesignP.PLUS_BOX_OUTLINE, 20));
        addButton.addActionListener(e -> {
            try {
                //TODO add get instance with singleton
                new OrderCreationTool(new OrderController());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(addButton, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(spacer6,gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        bottomPanel.add(spacer7,gbc);
        printReceiptButton = new JButton();
        printReceiptButton.setText("Print Receipt");
        printReceiptButton.setIcon(FontIcon.of(MaterialDesignP.PRINTER, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(printReceiptButton,gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(spacer8,gbc);
        totalField = new JFormattedTextField();
        totalField.setDragEnabled(false);
        totalField.setEditable(false);
        totalField.setEnabled(true);
        totalField.setText("€0.00");
        totalField.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(totalField, gbc);
        receiptTotalLabel = new JLabel();
        receiptTotalLabel.setText("TOTAL");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(receiptTotalLabel, gbc);
        rightPanel.setVisible(true);
        gridPanel.add(rightPanel);
        setRightPanel(rightPanel);
        setGridPanel(gridPanel);
        setGridPanel(gridPanel);
    }
}
