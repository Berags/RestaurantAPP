package edu.unifi.view;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends Window {

    private final JLabel splashScreenLabel = new JLabel("LOADING THE SOFTWARE");
    private final JLabel authors = new JLabel("Beragnoli - De Grazia - Orsucci");
    private final JLabel percentage = new JLabel("0%");

    public SplashScreen() throws Exception {
        super("", false, JFrame.DO_NOTHING_ON_CLOSE, 400, 300, 800, 300);
        setRootLayout(Layout.GRID, 3, 1);

        splashScreenLabel.setForeground(Color.WHITE);
        authors.setForeground(Color.WHITE);
        percentage.setForeground(Color.WHITE);

        splashScreenLabel.setHorizontalAlignment(JLabel.CENTER);
        authors.setHorizontalAlignment(JLabel.CENTER);
        percentage.setHorizontalAlignment(JLabel.CENTER);

        splashScreenLabel.setFont(new Font("Geo Sans Light", Font.PLAIN, 32));
        authors.setFont(new Font("Geo Sans Light", Font.PLAIN, 24));
        percentage.setFont(new Font("Geo Sans Light", Font.PLAIN, 38));

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getRoot().setBackground(Color.DARK_GRAY);
        addComponent(splashScreenLabel, null);
        addComponent(authors, null);
        addComponent(percentage, null);
        setUndecorated(true);
        centeredFrame(this);

        //setVisible(true);
        setSize(800, 300);
    }

    private void centeredFrame(javax.swing.JFrame objFrame) {
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();

        int iCoordX = (objDimension.width - objFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - objFrame.getHeight()) / 2;

        objFrame.setLocation(iCoordX, iCoordY);
    }

    public JLabel getPercentage() {
        return percentage;
    }
}
