package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.RoomToolController.RoomCreationToolController;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class RoomCreationTool extends Window {


    protected JTextField nameTextField;
    protected JLabel nameLabel;
    protected JButton createButton;
    protected JLabel titleLabel;
    private FontIcon createFontIcon;
    private JPanel gridPanel;

    private static volatile RoomCreationTool instance = null;

    protected RoomCreationTool(String title, int width, int height) throws Exception {
        super(title, false, JFrame.DISPOSE_ON_CLOSE, 0, 0, width, height);
        setUpUI();

        RoomCreationToolController roomCreationToolController = new RoomCreationToolController(this);
        roomCreationToolController.addObserver(Notifier.getInstance());
        createButton.addActionListener(roomCreationToolController);

        setVisible(true);
    }

    private void setUpUI() throws Exception {

        gridPanel = rootPane;
        gridPanel.setLayout(new BorderLayout());

        titleLabel = new JLabel();
        Font titleLabelFont = getFont(null, Font.BOLD, 22, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setText("Room Creation Tool");


        gridPanel.setLayout(new GridBagLayout());
        gridPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        nameLabel = new JLabel();
        Font nameLabelFont = getFont(null, -1, 20, nameLabel.getFont());
        if (nameLabelFont != null) nameLabel.setFont(nameLabelFont);
        nameLabel.setText("Name");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 50, 0, 0);
        gridPanel.add(nameLabel, gbc);

        nameTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 50);
        gridPanel.add(nameTextField, gbc);

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
        gridPanel.add(createButton, gbc);

        nameLabel.setLabelFor(nameTextField);

        createFontIcon = FontIcon.of(MaterialDesignP.PLUS_BOX_OUTLINE, 20);

        createButton.setIcon(createFontIcon);
    }

    public static RoomCreationTool getInstance(String title, int width, int height) throws Exception {
        RoomCreationTool thisInstance = instance;
        if (instance == null) {
            synchronized (RoomCreationTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new RoomCreationTool(title, width, height);
            }
        }
        return thisInstance;
    }

    @Override
    public void dispose() {
        instance = null;
        super.dispose();
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(JTextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public void setCreateButton(JButton createButton) {
        this.createButton = createButton;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }

    public void showResultDialog(String message, boolean messageType) {
        JOptionPane.showMessageDialog(null, message, messageType ? "Action successful" : "Severe Error!",
                messageType ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE,
                messageType ? FontIcon.of(MaterialDesignC.CHECK_CIRCLE_OUTLINE, 40, Color.BLUE) : FontIcon.of(MaterialDesignA.ALERT_RHOMBUS_OUTLINE, 40, Color.RED));
    }

    public static void setInstance(RoomCreationTool instance) {
        RoomCreationTool.instance = instance;
    }

    public JPanel getGridPanel(){return gridPanel;}

    protected void setGridPanel(JPanel panel){gridPanel = panel;}

}
