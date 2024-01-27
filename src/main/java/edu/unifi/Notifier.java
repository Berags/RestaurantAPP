package edu.unifi;

import edu.unifi.model.orm.DatabaseAccess;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class Notifier implements Observer {

    @Override
    public void update(Observable o, Object toDisplay){
        JDialog loadingDialog = new JDialog();
        loadingDialog.setLocationRelativeTo(null);
        loadingDialog.setTitle("Notification");
        loadingDialog.add(new JLabel(toDisplay.toString()));
        loadingDialog.pack();
        loadingDialog.setVisible(true);
    }
}
