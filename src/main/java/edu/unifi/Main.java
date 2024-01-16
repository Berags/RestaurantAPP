package edu.unifi;

import edu.unifi.api.security.aop.Authorize;
import edu.unifi.views.Home;
import edu.unifi.views.Login;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

import javax.swing.*;
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
        test();
        Login login = new Login();
        // Main thread is asleep while waiting for login thread to complete
        login.getLoginLatch().await();
        System.out.println("After login");
        new Home("Da Pippo");
    }

    @Authorize
    private static void test() {
        System.out.println("TEsting");
    }
}