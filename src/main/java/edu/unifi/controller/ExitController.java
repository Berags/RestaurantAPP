package edu.unifi.controller;

import edu.unifi.Notifier;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class ExitController extends Observable implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        setChanged();
        notifyObservers(Notifier.Message.build(MessageType.EXIT, "You are exiting the application"));
    }
}
