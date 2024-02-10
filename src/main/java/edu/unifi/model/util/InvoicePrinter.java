package edu.unifi.model.util;

import edu.unifi.model.entities.Check;
import edu.unifi.model.entities.Order;
import edu.unifi.model.entities.Table;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.OrderDAO;

import java.awt.*;
import java.awt.print.*;
import java.util.List;

public class InvoicePrinter implements Printable {
    private final Table table;
    private final PrinterJob pj;

    public InvoicePrinter(Table table, PrinterJob pj) {
        this.table = table;
        this.pj = pj;
    }
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        Check c = CheckDAO.getInstance().getValidCheckByTable(table);
        List<Order> orders = OrderDAO.getInstance().getOrdersByCheck(c);
        //pageFormat = getPageFormat(pj, orders);
        int r = orders.size();
        int result = NO_SUCH_PAGE;
        if (pageIndex == 0) {

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

            int y = 20;
            int yShift = 10;
            int headerRectHeight = 15;
            // int headerRectHeighta=40;


            g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
            g2d.drawString("-------------------------------------", 12, y);
            y += yShift;
            g2d.drawString("         RestaurantAPP        ", 12, y);
            y += yShift;
            g2d.drawString("   No 00000 Address Line  ", 12, y);
            y += yShift;
            g2d.drawString("   Address Line 02 FLORENCE, ITALY ", 12, y);
            y += yShift;
            g2d.drawString("   @RestaurantAPP ", 12, y);
            y += yShift;
            g2d.drawString("        +39 0000000000      ", 12, y);
            y += yShift;
            g2d.drawString("-------------------------------------", 12, y);
            y += headerRectHeight;

            g2d.drawString(" Item Name                  Price   ", 10, y);
            y += yShift;
            g2d.drawString("-------------------------------------", 10, y);
            y += headerRectHeight;
            float total = .0f;

            for (int s = 0; s < r; s++) {
                total += (float) (orders.get(s).getQuantity() * orders.get(s).getId().getDish().getPrice()) / 100;
                g2d.drawString(" " + orders.get(s).getId().getDish().getName() + "                            ", 10, y);
                y += yShift;
                g2d.drawString("      " + orders.get(s).getQuantity() + " * " + orders.get(s).getId().getDish().getPrice(), 10, y);
                g2d.drawString(String.valueOf((orders.get(s).getQuantity() * orders.get(s).getId().getDish().getPrice() / 100)), 160, y);
                y += yShift;
            }

            g2d.drawString("-------------------------------------", 10, y);
            y += yShift;
            g2d.drawString(" Total amount:               " + total + "   ", 10, y);
            y += yShift;
            g2d.drawString("-------------------------------------", 10, y);

            g2d.drawString("*************************************", 10, y);
            y += yShift;
            g2d.drawString("       THANK YOU COME AGAIN            ", 10, y);
            y += yShift;
            g2d.drawString("*************************************", 10, y);
            y += yShift;
            g2d.drawString(" CONTACT: info@restaurantapp.unifi.it ", 10, y);
            y += yShift;
            result = PAGE_EXISTS;
        }
        return result;
    }

    private PageFormat getPageFormat(PrinterJob pj, List<Order> orders) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = orders.size() * 1.5;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(9);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    private static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    private static double toPPI(double inch) {
        return inch * 72d;
    }
}

