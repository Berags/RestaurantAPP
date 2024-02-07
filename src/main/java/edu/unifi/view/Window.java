package edu.unifi.view;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class extends JFrame and implements Runnable to manage every window in the application.
 * It follows the Facade design pattern to simplify the interface for window management.
 */
public class Window extends JFrame implements Runnable {
    // Thread for running the window
    private final Thread t = new Thread(this);
    // Map to store components of the window
    private final HashMap<JComponent, Object> components = new HashMap<>();
    // Root panel of the window
    protected final JPanel rootPane = new JPanel();
    // Menu bar of the window
    private JMenuBar menuBar = new JMenuBar();

    // Enum for layout types
    public enum Layout {BORDER, FLOW, GRID}

    /**
     * Constructor for Window class.
     *
     * @param title                the title of the window
     * @param hasMenu              whether the window has a menu
     * @param defaultExitOperation the default operation on window close
     * @param x                    the x coordinate of the screen
     * @param y                    the y coordinate of the screen
     * @param width                the width of the frame
     * @param height               the height of the frame
     */
    public Window(String title, boolean hasMenu, int defaultExitOperation, int x, int y, int width, int height) {
        super(title);

        getContentPane().add(rootPane);
        setBounds(x, y, width, height);
        setDefaultCloseOperation(defaultExitOperation);
        if (hasMenu) setJMenuBar(menuBar);

        t.setName(title);
        setLocationRelativeTo(null);
    }

    /**
     * Adds a component to the root panel.
     *
     * @param component the component to add
     * @param args      the arguments for the component
     */
    public final <T> void addComponent(JComponent component, T args) {
        rootPane.add(component, args);
        update();
    }

    /**
     * Adds menu entries to the menu bar.
     *
     * @param menuEntries the menu entries to add
     */
    public void addMenuEntries(Component[] menuEntries) {
        for (Component entry : menuEntries)
            menuBar.add(entry);
        update();
    }

    /**
     * Updates the window by invalidating, validating, and repainting it.
     */
    private void update() {
        invalidate();
        validate();
        repaint();
    }

    /**
     * Sets the layout of the root panel.
     *
     * @param layout the layout to set
     * @param args   the arguments for the layout
     * @throws Exception if the arguments for the layout are incorrect
     */
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

    /**
     * Returns a font with the specified name, style, and size.
     * If the font name is not available, it uses the current font's name.
     * If the style or size is negative, it uses the current font's style or size.
     *
     * @param fontName    the name of the font
     * @param style       the style of the font
     * @param size        the size of the font
     * @param currentFont the current font
     * @return the font with the specified name, style, and size
     */
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

    /**
     * Returns the root panel of the window.
     *
     * @return the root panel of the window
     */
    public JPanel getRoot() {
        return rootPane;
    }

    /**
     * The run method for the Runnable interface.
     * Currently, it does nothing.
     */
    @Override
    public void run() {
        // Do we really need a thread?
    }
}
