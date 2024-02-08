package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.Check;
import edu.unifi.model.entities.Table;
import edu.unifi.model.util.InvoicePrinter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Observable;

public class TableController {
    public static class PrintCheckController extends Observable implements ActionListener {
        private final Table table;

        public PrintCheckController(Table table) {
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(new InvoicePrinter(table, job));
                boolean doPrint = job.printDialog();
                if (doPrint) {
                    job.print();
                }
            } catch (PrinterException ex) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Error while printing check: " + ex.getMessage()));
            }
        }
    }
}
