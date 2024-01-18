package edu.unifi.api.graphics;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Locale;

public class Window extends JFrame implements Runnable {
    private final Thread t = new Thread(this);
    private final HashMap<JComponent, Object> components = new HashMap<>();
    private final JPanel rootPane = new JPanel();
    @Setter
    private JMenuBar menuBar = new JMenuBar();

    public enum Layout {BORDER, FLOW, GRID}


    // TODO: refactor constructor to get Rectangle instead of int... bounds
    public Window(String title, boolean hasMenu, int defaultExitOperation, int... bounds) {
        super(title);

        getContentPane().add(rootPane);
        if (bounds.length == 4) setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        else setBounds(0, 0, 1280, 720);
        setDefaultCloseOperation(defaultExitOperation);
        if (hasMenu) setJMenuBar(menuBar);

        setVisible(true);

        t.setName(title);
    }

    public final <T> void addComponent(JComponent component, T args) {
        rootPane.add(component, args);
        update();
    }

    public void addMenuEntries(JMenu... menuEntries) {
        for (JMenu entry : menuEntries)
            menuBar.add(entry);
        update();
    }

    private void update() {
        invalidate();
        validate();
        repaint();
    }

    public void setRootLayout(Layout layout, int... args) throws Exception {
        switch (layout) {
            case BORDER -> {
                if (args.length == 0) {
                    rootPane.setLayout(new BorderLayout());
                } else if (args.length == 2) {
                    rootPane.setLayout(new BorderLayout(args[0], args[1]));
                } else {
                    throw new Exception("Border layout requires 0 or 2 parameters.");
                }
            }
            case FLOW -> {
                rootPane.setLayout(new FlowLayout());
            }
            case GRID -> {
                if (args.length == 2) {
                    rootPane.setLayout(new GridLayout(args[0], args[1]));
                } else if (args.length == 4) {
                    rootPane.setLayout(new GridLayout(args[0], args[1], args[2], args[3]));
                } else {
                    throw new Exception("Grid layout requires 2 or 4 parameters.");
                }
            }
        }
    }

    public static Font getFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    public JPanel getRoot() {
        return rootPane;
    }

    @Override
    public void run() {
        // Do we really need a thread?
    }
}
