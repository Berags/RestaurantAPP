package edu.unifi;

import edu.unifi.api.dco.DatabaseAccess;
import edu.unifi.api.security.Roles;
import edu.unifi.api.security.aop.Authorize;
import edu.unifi.entities.Room;
import edu.unifi.entities.Table;
import edu.unifi.views.*;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
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

    public static void main(String[] args) {
        // Initiating database connection pool
        DatabaseAccess.initiate();
        Room r = new Room();
        Table t = new Table();
        try {
            //UserRepository userRepository = UserRepository.getInstance();
            //List<User> users = userRepository.getAll();
            //users.forEach(user -> System.out.printf(String.valueOf(user.getId())));
            //userRepository.delete(User.builder().build());
            //new TableUpdateTool();
            home = new Home("Restaurant Name");
        /*Login login = new Login();
        // Main thread is asleep while waiting for login thread to complete
        login.getLoginLatch().await();
        System.out.println("After login");
        new Home("Da Pippo");*/
            //new TableUpdateTool();
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