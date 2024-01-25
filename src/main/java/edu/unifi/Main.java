package edu.unifi;

import edu.unifi.model.entities.User;
import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.UserDAO;
import edu.unifi.model.util.security.PasswordManager;
import edu.unifi.model.util.security.Roles;
import edu.unifi.model.util.security.aop.Authorize;
import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.Table;
import edu.unifi.view.*;
import edu.unifi.view.SplashScreen;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class Main {
    // Todo: replace with slf4j
    static Logger log = Logger.getLogger(Main.class.getName());
    private static Home home;

    static {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));
        } catch (UnsupportedLookAndFeelException e) {
            log.info(e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Initiating database connection pool
        DatabaseAccess.initiate();
        try {
            Login loginView = new Login();
            loginView.getLoginLatch().await();
        } catch (Exception e) {
            log.severe(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Severe Error!", JOptionPane.ERROR_MESSAGE, FontIcon.of(MaterialDesignA.ALERT_RHOMBUS_OUTLINE, 40, Color.RED));
        } finally {
            // Cleaning up database connection pool
            DatabaseAccess.terminate();
        }
    }

    @Authorize(role = Roles.ADMIN)
    private static void test() {
        log.info("test");
    }
}