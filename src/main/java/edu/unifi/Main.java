package edu.unifi;

import edu.unifi.api.dco.DatabaseAccess;
import edu.unifi.entities.User;
import edu.unifi.repositories.UserRepository;
import edu.unifi.views.DishCreationTool;
import edu.unifi.views.Home;
import edu.unifi.views.TableCreationTool;
import edu.unifi.views.TableUpdateTool;
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

    public static void main(String[] args) {
        // Initiating database connection pool
        DatabaseAccess.initiate();
        try {
            //UserRepository userRepository = UserRepository.getInstance();
            //List<User> users = userRepository.getAll();
            //users.forEach(user -> System.out.printf(String.valueOf(user.getId())));
            //userRepository.delete(User.builder().build());
            //new TableUpdateTool();
            new Home("Restaurant Name");
        /*Login login = new Login();
        // Main thread is asleep while waiting for login thread to complete
        login.getLoginLatch().await();
        System.out.println("After login");
        new Home("Da Pippo");*/
        } catch (Exception e) {
            log.severe(e.getMessage());
        } finally {
            // Cleaning up database connection pool
            DatabaseAccess.terminate();
        }
    }
}