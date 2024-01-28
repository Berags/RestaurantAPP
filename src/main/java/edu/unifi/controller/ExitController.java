package edu.unifi.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class ExitController extends Observable implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        setChanged();
        notifyObservers(MessageType.EXIT);
    }
}
