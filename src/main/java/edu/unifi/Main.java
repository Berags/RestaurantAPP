package edu.unifi;

import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.util.security.Roles;
import edu.unifi.model.util.security.aop.Authorize;
import edu.unifi.view.Home;
import edu.unifi.view.Login;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.swing.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final CountDownLatch exitLatch = new CountDownLatch(1);
    private static Home home;

    static {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));
            UIManager.put("Button.mouseHoverEnable", false);
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

        //you need to execute the queries below only one time to set the courses
        /*
        TypeOfCourse type1 = new TypeOfCourse();
        TypeOfCourse type2 = new TypeOfCourse();
        TypeOfCourse type3 = new TypeOfCourse();
        TypeOfCourse type4 = new TypeOfCourse();
        TypeOfCourse type5 = new TypeOfCourse();
        TypeOfCourse type6 = new TypeOfCourse();
        TypeOfCourse type7 = new TypeOfCourse();
        TypeOfCourseDAO t = new TypeOfCourseDAO();

        type1.setName("Appetizer");
        t.insert(type1);
        type2.setName("First Plate");
        t.insert(type2);
        type3.setName("Pizza");
        t.insert(type3);
        type4.setName("Second Plate");
        t.insert(type4);
        type5.setName("Side Dish");
        t.insert(type5);
        type6.setName("Dessert");
        t.insert(type6);
        type7.setName("Service");
        t.insert(type7);
        */

        loadingDialog.setVisible(false);
        try {
            Login loginView = new Login();
            loginView.getLoginLatch().await();
            home = new Home("Test");
            exitLatch.await();
        } catch (Exception e) {
            log.error(e.getMessage());
            JLabel label = new JLabel(e.getMessage());
            label.setFont(new Font("Arial", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Severe Error!", JOptionPane.ERROR_MESSAGE, FontIcon.of(MaterialDesignA.ALERT_RHOMBUS_OUTLINE, 40, Color.RED));
        } finally {
            // Cleaning up database connection pool
            loadingDialog.setVisible(true);
            DatabaseAccess.terminate();
            loadingDialog.setVisible(false);
            System.exit(0);
        }
    }

    @Authorize(role = Roles.ADMIN)
    private static void test() {
        log.info("test");
    }

    public static void notifyExit() {
        exitLatch.countDown();
    }
}