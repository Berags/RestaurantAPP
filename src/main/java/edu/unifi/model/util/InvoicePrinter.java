package edu.unifi.model.util;

import edu.unifi.model.entities.Check;
import edu.unifi.model.entities.Order;
import edu.unifi.model.entities.Table;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.OrderDAO;

import java.awt.*;
import java.awt.print.*;
import java.text.DecimalFormat;
import java.util.List;

/**
 * This class is responsible for printing invoices.
 * It implements the Printable interface which provides a method for rendering an object to a Graphics object.
 */
public class InvoicePrinter implements Printable {
    private final Table table;
    private final PrinterJob pj;

    /**
     * Constructor for InvoicePrinter.
     * @param table The table for which the invoice is to be printed.
     * @param pj The PrinterJob that is to be used for printing.
     */
    public InvoicePrinter(Table table, PrinterJob pj) {
        this.table = table;
        this.pj = pj;
    }

    /**
     * This method is called to print the page at the specified index into the specified Graphics context.
     * @param graphics the context into which the page is drawn
     * @param pageFormat the size and orientation of the page being drawn
     * @param pageIndex the zero based index of the page to be drawn
     * @return PAGE_EXISTS if the page is rendered successfully, or NO_SUCH_PAGE if pageIndex specifies a non-existent page.
     */
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex != 0) return NO_SUCH_PAGE;

        Check c = CheckDAO.getInstance().getValidCheckByTable(table);
        List<Order> orders = OrderDAO.getInstance().getOrdersByCheck(c);

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

        int y = 20;
        int yShift = 10;
        int headerRectHeight = 15;

        g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
        printHeader(g2d, y, yShift, headerRectHeight);
        y += headerRectHeight * 6;

        float total = printOrders(g2d, orders, y, yShift, headerRectHeight);
        y += yShift * (orders.size() * 2 + 3);

        printFooter(g2d, total, y, yShift);

        return PAGE_EXISTS;
    }

    /**
     * This method is used to print the header of the invoice.
     * @param g2d the Graphics2D context into which the header is drawn
     * @param y the y-coordinate of the header
     * @param yShift the shift in the y-coordinate for each line
     * @param headerRectHeight the height of the header rectangle
     */
    private void printHeader(Graphics2D g2d, int y, int yShift, int headerRectHeight) {
        printLine(g2d, "-------------------------------------", 12, y);
        y += yShift;
        printLine(g2d, "         RestaurantAPP        ", 12, y);
        y += yShift;
        printLine(g2d, "   No 00000 Address Line  ", 12, y);
        y += yShift;
        printLine(g2d, "   Address Line 02 FLORENCE, ITALY ", 12, y);
        y += yShift;
        printLine(g2d, "   @RestaurantAPP ", 12, y);
        y += yShift;
        printLine(g2d, "        +39 0000000000      ", 12, y);
        y += yShift;
        printLine(g2d, "-------------------------------------", 12, y);
        y += headerRectHeight;
    }

    /**
     * This method is used to print the orders in the invoice.
     * @param g2d the Graphics2D context into which the orders are drawn
     * @param orders the list of orders to be printed
     * @param y the y-coordinate of the orders
     * @param yShift the shift in the y-coordinate for each line
     * @param headerRectHeight the height of the header rectangle
     * @return the total amount of the orders
     */
    private float printOrders(Graphics2D g2d, List<Order> orders, int y, int yShift, int headerRectHeight) {
        printLine(g2d, " Item Name                  Price   ", 10, y);
        y += yShift;
        printLine(g2d, "-------------------------------------", 10, y);
        y += headerRectHeight;

        float total = .0f;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        for (Order order : orders) {
            total += (float) (order.getQuantity() * order.getId().getDish().getPrice()) / 100;
            printLine(g2d, " " + order.getId().getDish().getName() + "                            ", 10, y);
            y += yShift;
            printLine(g2d, "      " + order.getQuantity() + " * " + df.format(order.getId().getDish().getPrice() / 100.f), 10, y);
            printLine(g2d, df.format((order.getQuantity() * order.getId().getDish().getPrice() / 100.f)), 160, y);
            y += yShift;
        }

        printLine(g2d, "-------------------------------------", 10, y);
        y += yShift;

        return total;
    }

    /**
     * This method is used to print the footer of the invoice.
     * @param g2d the Graphics2D context into which the footer is drawn
     * @param total the total amount of the orders
     * @param y the y-coordinate of the footer
     * @param yShift the shift in the y-coordinate for each line
     */
    private void printFooter(Graphics2D g2d, float total, int y, int yShift) {
        printLine(g2d, " Total amount:               " + total + "   ", 10, y);
        y += yShift;
        printLine(g2d, "-------------------------------------", 10, y);
        y += yShift;
        printLine(g2d, "*************************************", 10, y);
        y += yShift;
        printLine(g2d, "       THANK YOU COME AGAIN            ", 10, y);
        y += yShift;
        printLine(g2d, "*************************************", 10, y);
        y += yShift;
        printLine(g2d, " CONTACT: info@restaurantapp.unifi.it ", 10, y);
        y += yShift;
    }

    /**
     * This method is used to print a line in the invoice.
     * @param g2d the Graphics2D context into which the line is drawn
     * @param text the text to be printed
     * @param x the x-coordinate of the line
     * @param y the y-coordinate of the line
     */
    private void printLine(Graphics2D g2d, String text, int x, int y) {
        g2d.drawString(text, x, y);
    }
}