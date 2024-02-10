package edu.unifi.view;

import edu.unifi.controller.RoomToolController;
import edu.unifi.model.entities.Room;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Objects;

//TODO: Refactoring con DishVIew?
public class RoomView extends Window {

    private JButton addButton;
    private JLabel numberLabel;
    private JTextField searchTextField;
    private JLabel nameLabel;
    private JLabel actionLabel;
    private final JScrollPane listScroller = new JScrollPane();

    private JPanel panel1;
    private JPanel panel2;
    private JPanel listPanel;
    private static RoomView instance;

    private java.util.List<Room> filteredRooms;


    private RoomView() {

        super("Rooms", false, DISPOSE_ON_CLOSE, 0, 0, 600, 600);
        try {
        setupUI();
        } catch(Exception e) {}
        setVisible(true);
    }

    public static boolean isDisposed() {
        return Objects.isNull(instance);
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
                RoomCreationTool.getInstance("Room creation tool", 400, 300);
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
        nameLabel = new JLabel();
        nameLabel.setText("Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(nameLabel, gbc);
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
        numberLabel = new JLabel();
        numberLabel.setText("Number");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(numberLabel, gbc);
        actionLabel = new JLabel();
        actionLabel.setText("Action");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(actionLabel, gbc);
    }

    public static RoomView getInstance() {
        RoomView thisInstance = instance;
        if (instance == null) {
            synchronized (RoomView.class) {
                if (thisInstance == null)
                    instance = thisInstance = new RoomView();
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
        filteredRooms = new RoomToolController().getFilteredRooms(searchTextField.getText() == null ? "" : searchTextField.getText());
        listPanel = new JPanel(new GridLayout(filteredRooms.size(), 1));
        int index = 0;

        for (var r : filteredRooms) {
            try {
                RoomItem RI = new RoomItem(r,index);
                this.listPanel.add(RI.getListPanel());
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

}
