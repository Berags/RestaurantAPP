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
    protected JTextField searchTextField;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JLabel actionLabel;
    private DishController dishController;
    protected final JScrollPane listScroller = new JScrollPane();

    private JPanel panel1;
    protected JPanel panel2;
    protected JPanel listPanel;
    private static DishView instance;

    protected java.util.List<Dish> filteredDishes;

    /**
     * To have all the dishItems, complete with buildList()
     * @param dishController
     * @throws Exception
     */
    private DishView(DishController dishController) throws Exception {
        super("Dishes", false, DISPOSE_ON_CLOSE, 0, 0, 600, 600);
        this.dishController = dishController;

        setupUI();
        setVisible(true);
    }

    protected DishView() throws Exception{
        super("Order creation tool",false,DISPOSE_ON_CLOSE,0,0,600,600);
        setupUI();
    }

    private void setupUI() throws Exception {
        setRootLayout(Layout.BORDER);
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        addComponent(panel1, BorderLayout.NORTH);
        addButton = new JButton();
        addButton.setText("add");
        addButton.setIcon(FontIcon.of(MaterialDesignP.PLUS_BOX, 20));

        addButton.addActionListener(e -> {
            try {
                DishCreationTool.getInstance("Dish creation tool",400,300);
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

    public static DishView getInstance(DishController dishController) throws Exception {
        DishView thisInstance = instance;
        if (instance == null) {
            synchronized (DishView.class) {
                if (thisInstance == null)
                    instance = thisInstance = new DishView(dishController);
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
        filteredDishes = dishController.getFilteredDishes(searchTextField.getText() == null ? "" : searchTextField.getText());
        listPanel = new JPanel(new GridLayout(filteredDishes.size(), 1));
        int index = 0;

        for (var d : filteredDishes) {
            try {
                DishItem DI = new DishItem(d,index);
                this.listPanel.add(DI.getListPanel());
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
    public DishController getDishController(){return dishController;}

    protected JPanel getPanel1(){return panel1;}
    protected JButton getAddButton(){return addButton;}
    protected java.util.List<Dish> getFilteredDishes(){return filteredDishes;}
    protected void setFilteredDishes(java.util.List<Dish> filteredDishes){this.filteredDishes = filteredDishes;}
    protected JTextField getSearchTextField(){return searchTextField;}
    protected JScrollPane getListScroller(){return listScroller;}
    protected JPanel getListPanel(){return listPanel;}
    protected JPanel getPanel2(){return panel2;}

    protected void setListPanel(JPanel listPanel){this.listPanel = listPanel;}
}
