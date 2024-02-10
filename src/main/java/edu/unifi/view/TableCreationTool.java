package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.TableToolController;
import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.TableState;
import edu.unifi.model.orm.dao.RoomDAO;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableCreationTool extends Window {
    protected JTextField nameTextField;
    protected JSpinner nOfSeatsSpinner;
    protected JComboBox stateComboBox;
    protected JLabel nameLabel;
    private JLabel nOfSeatsLabel;
    protected JButton createButton;
    private JLabel stateLabel;
    protected JLabel titleLabel;
    private FontIcon createFontIcon;
    private JLabel roomLabel;
    protected JComboBox<String> roomComboBox;
    private List<Room> rooms;
    private JPanel gridPanel;

    private JPanel rightPanel;

    private JPanel leftPanel;

    private static volatile TableCreationTool instance = null;

    protected TableCreationTool(String title, int width, int height) throws Exception {
        super(title, false, JFrame.DISPOSE_ON_CLOSE, 0, 0, width, height);
        setUpUI();

        TableToolController.TableCreationToolController tableCreationToolController = new TableToolController.TableCreationToolController(this);
        tableCreationToolController.addObserver(Notifier.getInstance());
        createButton.addActionListener(tableCreationToolController);

        setVisible(true);
    }

    private void setUpUI() throws Exception {

        gridPanel = (JPanel) getContentPane();
        gridPanel.setLayout(new BorderLayout());

        titleLabel = new JLabel();
        Font titleLabelFont = getFont(null, Font.BOLD, 22, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setText("Table Creation Tool");
        leftPanel = new JPanel();

        gridPanel.add(leftPanel);

        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
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
        leftPanel.add(nameLabel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        leftPanel.add(spacer1, gbc);
        nameTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 50);
        leftPanel.add(nameTextField, gbc);
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
        leftPanel.add(nOfSeatsLabel, gbc);
        //TODO: check if the nOfSeat is 0?
        nOfSeatsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 0, 50);
        leftPanel.add(nOfSeatsSpinner, gbc);


        ArrayList<TableState> listOfState = new ArrayList<>(Arrays.asList(TableState.values()));
        stateComboBox = new JComboBox<>(listOfState.toArray());

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 0, 50);
        leftPanel.add(stateComboBox, gbc);
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
        leftPanel.add(stateLabel, gbc);

        roomLabel = new JLabel("Room");
        Font roomLabelFont = getFont(null, -1, 18, roomLabel.getFont());
        if (roomLabelFont != null) roomLabel.setFont(roomLabelFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(15, 50, 0, 0);
        leftPanel.add(roomLabel, gbc);

        roomComboBox = new JComboBox<>();
        rooms = RoomDAO.getInstance().getAll();
        for (Room room : rooms) {
            roomComboBox.addItem(room.getName());
        }
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 0, 50);
        leftPanel.add(roomComboBox, gbc);

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
        leftPanel.add(createButton, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        leftPanel.add(spacer2, gbc);
        nameLabel.setLabelFor(nameTextField);
        nOfSeatsLabel.setLabelFor(nOfSeatsSpinner);
        stateLabel.setLabelFor(stateComboBox);

        nameTextField.setName("nameTextField");
        nOfSeatsSpinner.setName("nOfSeatsSpinner");
        roomComboBox.setName("roomComboBox");
        stateComboBox.setName("stateComboBox");
        createButton.setName("createButton");

        createFontIcon = FontIcon.of(MaterialDesignP.PLUS_BOX_OUTLINE, 20);

        createButton.setIcon(createFontIcon);

    }

    /**
     * To implement the Singleton: we don't want to create a window
     * every time the user clicks on "add table", we need only one.
     *
     * @return
     * @throws Exception
     */
    public static TableCreationTool getInstance(String title, int width, int height) throws Exception {
        TableCreationTool thisInstance = instance;
        if (instance == null) {
            synchronized (TableCreationTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new TableCreationTool(title, width, height);
            }
        }
        return thisInstance;
    }

    /**
     * to "reset" the Singleton
     */
    @Override
    public void dispose() {
        instance = null;
        super.dispose();
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JSpinner getNOfSeatsSpinner() {
        return nOfSeatsSpinner;
    }

    public JComboBox getStateComboBox() {
        return stateComboBox;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JComboBox<String> getRoomComboBox() {
        return roomComboBox;
    }

    public Room getRoom() {
        return rooms.get(roomComboBox.getSelectedIndex());
    }

    public static void setInstance(TableCreationTool instance) {
        TableCreationTool.instance = instance;
    }

    public JPanel getGridPanel(){return gridPanel;}

    protected void setGridPanel(JPanel panel){gridPanel = panel;}

    protected JPanel getLeftPanel(){return leftPanel;}

    protected void setRightPanel(JPanel rightPanel){this.rightPanel = rightPanel;}
}
