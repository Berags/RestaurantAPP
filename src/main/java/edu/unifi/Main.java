package edu.unifi;

import edu.unifi.api.security.Authorize;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

import javax.swing.*;
import java.util.logging.Logger;

public class Main {
    static Logger log = Logger.getLogger(Main.class.getName());

    static {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));// by including the https://github.com/material-ui-swing/DarkStackOverflowTheme
        } catch (UnsupportedLookAndFeelException e) {
            log.info(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        //new Home("Home");
        //new Login();
        test();
    }

    @Authorize
    public static void test() {
        System.out.println("I am in the method");
    }
}