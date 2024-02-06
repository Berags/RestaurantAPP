package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.OrderController;
import edu.unifi.controller.TableController;
import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.Order;
import edu.unifi.model.entities.Table;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignU;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TableUpdateTool extends TableCreationTool {
    private JPanel rightPanel;
    private JButton addButton;

    private JButton checkButton;

    private JButton resetButton;
    private JButton printReceiptButton;
    private JFormattedTextField totalField;
    private JPanel labelPanel;
    private JLabel dishLabel;
    private JLabel quantityLabel;
    private JLabel totalLabel;
    private JLabel actionsLabel;
    private JPanel orderPanel;
    private JScrollPane listScroller;

    private int orderIndex = 0;

    private JPanel listPanel;
    private JPanel bottomPanel;
    private JLabel receiptTotalLabel;

    private TableController tableController;

    private java.util.List<Order> orders = new ArrayList<>();
    private java.util.List<OrderListItem> orderItems = new ArrayList<>();

    public TableUpdateTool(String title, Table table, int width, int height) throws Exception {
        super(title, width, height);
       // this.table = table;
        setUpRightUI(table);
        ActionListener[] actionListeners = getCreateButton().getActionListeners();
        for (ActionListener a : actionListeners)
            getCreateButton().removeActionListener(a);

        titleLabel.setText("Table Update Tool");
        nameTextField.setText(table.getName());
        nOfSeatsSpinner.setValue(table.getNOfSeats());
        stateComboBox.setSelectedItem(table.getState());
        roomComboBox.setSelectedItem(table.getRoom().getName());

        createButton.setText("Update");
        createButton.setIcon(FontIcon.of(MaterialDesignU.UPDATE, 20));

        tableController = new TableController(table, this);
        tableController.addObserver(Notifier.getInstance());
        createButton.addActionListener(tableController);

       buildOrdersList(table);

        setVisible(true);
    }

    private void setUpRightUI(Table table) {

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
        labelPanel.setLayout(new GridLayout(1,4));

        rightPanel.add(labelPanel, BorderLayout.NORTH);
       // labelPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-2104859)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        dishLabel = new JLabel();
        Font dishLabelFont = getFont(null, Font.BOLD, 18, dishLabel.getFont());
        if (dishLabelFont != null) dishLabel.setFont(dishLabelFont);
        dishLabel.setText("Dish");

        GridBagConstraints gbc;

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        labelPanel.add(dishLabel, gbc);
        quantityLabel = new JLabel();
        Font quantityLabelFont = getFont(null, Font.BOLD, 18, quantityLabel.getFont());
        if (quantityLabelFont != null) quantityLabel.setFont(quantityLabelFont);
        quantityLabel.setText("Quantity");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        labelPanel.add(quantityLabel, gbc);
        totalLabel = new JLabel();
        Font totalLabelFont = getFont(null, Font.BOLD, 18, totalLabel.getFont());
        if (totalLabelFont != null) totalLabel.setFont(totalLabelFont);
        totalLabel.setText("Total");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        labelPanel.add(totalLabel, gbc);
        actionsLabel = new JLabel();
        Font actionsLabelFont = getFont(null, Font.BOLD, 18, actionsLabel.getFont());
        if (actionsLabelFont != null) actionsLabel.setFont(actionsLabelFont);
        actionsLabel.setText("Actions");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        labelPanel.add(actionsLabel, gbc);

        listScroller = new JScrollPane();

        orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout(0,0));
        orderPanel.add(listScroller);
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
                new OrderCreationTool(new OrderController(),this,table);
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
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        checkButton = new JButton();
        checkButton.setText("New check");
        checkButton.setIcon(FontIcon.of(MaterialDesignP.PLUS_BOX_OUTLINE, 20));
        checkButton.addActionListener(new OrderController.CheckCreationController(table));
        bottomPanel.add(checkButton, gbc);
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

        //TODO: find the right icon
        resetButton = new JButton();
        resetButton.setText("Reset Receipt");
        resetButton.setIcon(FontIcon.of(MaterialDesignP.POKEBALL, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        resetButton.addActionListener(new OrderController.CheckResetController(this, table));
        bottomPanel.add(resetButton,gbc);


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

    public void buildOrdersList(Table table){

        float total=0;

        orders = tableController.getTableOrders(table);
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(orders.size(),1));
        listScroller.setViewportView(listPanel);

        for(var o:orders){
            OrderListItem OLI = new OrderListItem(o.getId().getDish(),o.getQuantity(),o.getId(),this, table);
            total+= (Float.parseFloat(OLI.quantityLabel.getText())*o.getId().getDish().getPrice()/10);
            orderItems.add(OLI);
            listPanel.add(OLI.getListPanel());
        }

        System.out.println(total);

        String totalString = ((Float)total).toString();
        totalString = totalString.replace(".", "");
        String intString = totalString.substring(0,totalString.length()-2);
        String decimalString = totalString.substring(totalString.length()-2);

        totalField.setText(intString + "." + decimalString);
    }

    public JScrollPane getListScroller(){return listScroller;}
    public JPanel getListPanel(){return listPanel;}

    public int getOrderIndex(){return orderIndex;}
    public void setOrderIndex(int orderIndex){this.orderIndex = orderIndex;}
    public java.util.List<OrderListItem> getOrderItems(){return orderItems;}

    public java.util.List<Order> getOrders(){return orders;}
}
