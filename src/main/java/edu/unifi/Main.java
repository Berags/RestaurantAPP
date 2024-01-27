package edu.unifi;

import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.UserDAO;
import edu.unifi.model.util.security.PasswordManager;
import edu.unifi.model.util.security.Roles;
import edu.unifi.model.util.security.aop.Authorize;
import edu.unifi.view.Home;
import edu.unifi.view.Login;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;
import org.kordamp.ikonli.swing.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.unifi.model.entities.*;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class Main {
    static Logger log = LoggerFactory.getLogger(Main.class);
    private static final CountDownLatch exitLatch = new CountDownLatch(1);
    private static final Notifier notifier = new Notifier();
    private static Home home;

    static {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));
        } catch (UnsupportedLookAndFeelException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        JDialog loadingDialog = new JDialog();
        loadingDialog.setLocationRelativeTo(null);
        loadingDialog.setTitle("Loading...");
        loadingDialog.add(new JLabel("Please wait while the application is loading..."));
        loadingDialog.pack();
        loadingDialog.setVisible(true);
        // Initiating database connection pool
        DatabaseAccess.initiate();

        loadingDialog.setVisible(false);
        try {
            Login loginView = new Login();
            loginView.getLoginLatch().await();
            loginView.setVisible(true);
            home = new Home("Test");
            loginView.setVisible(false);
            exitLatch.await();

        } catch (Exception e) {
            log.error(e.getMessage());
            JLabel label = new JLabel(e.getMessage());
            label.setFont(new Font("Arial", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Severe Error!", JOptionPane.ERROR_MESSAGE, FontIcon.of(MaterialDesignA.ALERT_RHOMBUS_OUTLINE, 40, Color.RED));
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