package edu.unifi;

import edu.unifi.api.dco.DatabaseAccess;
import edu.unifi.api.security.aop.Authorize;
import edu.unifi.entities.User;
import edu.unifi.views.Home;
import edu.unifi.views.Login;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

import javax.swing.*;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    static Logger log = Logger.getLogger(Main.class.getName());

    static {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));
        } catch (UnsupportedLookAndFeelException e) {
            log.info(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        List<User> users = (List<User>) DatabaseAccess.getInstance().query("From User", User.class);
        users.forEach(user -> System.out.printf(String.valueOf(user.getId())));
        /*Login login = new Login();
        // Main thread is asleep while waiting for login thread to complete
        login.getLoginLatch().await();
        System.out.println("After login");
        new Home("Da Pippo");*/
    }
}