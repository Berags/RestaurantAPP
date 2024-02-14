package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.Check;
import edu.unifi.model.entities.Table;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.OrderDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.model.util.InvoicePrinter;
import edu.unifi.view.TableUpdateTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Observable;

/**
 * This class is responsible for managing the checks in the system.
 * It contains three inner classes each responsible for a specific action: creating, resetting and printing checks.
 */
public class CheckToolController {

    private static TableUpdateTool commonTableUpdateTool;
    private static Table commonTable;
    private static Check commonCheck;

    /**
     * This class is responsible for creating checks.
     * It extends Observable and implements ActionListener.
     */
    public static class CheckCreationController extends Observable implements ActionListener {

        /**
         * Constructor for CheckCreationController.
         * @param table The table for which the check is to be created.
         */
        public CheckCreationController(Table table) {
            commonTable = table;
        }

        /**
         * This method is called when an action is performed.
         * It creates a new check for the table if it doesn't already have one.
         * @param e The action event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Check if the table already has a check
            if (!java.util.Objects.isNull(CheckDAO.getInstance().getValidCheckByTable(commonTable))) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "This table already has an associated check.\n " +
                        "You can clean it using the dedicated button"));
                return;
            }

            // Create a new check for the table
            commonTable = TableDAO.getInstance().getById(commonTable.getId());
            commonCheck = new Check();
            commonCheck.setIssueDate(java.time.LocalDateTime.now());
            commonCheck.setTable(commonTable);
            commonTable.getChecks().add(commonCheck);
            CheckDAO.getInstance().insert(commonCheck);

            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ADD_DISH, "New check created successfully"));

        }
    }

    /**
     * This class is responsible for resetting checks.
     * It extends Observable and implements ActionListener.
     */
    public static class CheckResetController extends Observable implements ActionListener {

        /**
         * Constructor for CheckResetController.
         * @param tableUpdateTool The tool used to update the table.
         * @param table The table for which the check is to be reset.
         */
        public CheckResetController(TableUpdateTool tableUpdateTool, Table table) {

            commonTableUpdateTool = tableUpdateTool;
            commonTable = table;

        }

        /**
         * This method is called when an action is performed.
         * It resets the check for the table if it exists.
         * @param e The action event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Check if the table has a check to delete
            if (java.util.Objects.isNull(CheckDAO.getInstance().getValidCheckByTable(commonTable))) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "There isn't any check to delete!"));
                return;
            }

            // Delete all the orders present in the check
            for (var a : commonTableUpdateTool.getOrders())
                OrderDAO.getInstance().delete(a);

            Check check1 = CheckDAO.getInstance().getValidCheckByTable(commonTable);

            commonTable.getChecks().removeAll(commonTable.getChecks());

            CheckDAO.getInstance().delete(check1);

            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.CLEAN_CHECK, "Orders and check deleted successfully"));
            commonTableUpdateTool.buildOrdersList(commonTable);
        }
    }

    /**
     * This class is responsible for printing checks.
     * It extends Observable and implements ActionListener.
     */
    public static class PrintCheckController extends Observable implements ActionListener {
        private final Table table;

        /**
         * Constructor for PrintCheckController.
         * @param table The table for which the check is to be printed.
         */
        public PrintCheckController(Table table) {
            this.table = table;
        }

        /**
         * This method is called when an action is performed.
         * It prints the check for the table if it exists and is not empty.
         * @param e The action event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Check if the table has a check to print and if the check is not empty
            if (java.util.Objects.isNull(CheckDAO.getInstance().getValidCheckByTable(commonTable)) ||
                    commonTableUpdateTool.getOrders().isEmpty()) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "This table hasn't an associated check " +
                        "\nto print yet or the check is empty"));
                return;
            }

            // Print the check
            try {
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(new InvoicePrinter(table, job));
                boolean doPrint = job.printDialog();
                if (doPrint) {
                    job.print();
                    commonCheck = CheckDAO.getInstance().getValidCheckByTable(commonTable);
                    commonCheck.setIssueDate(java.time.LocalDateTime.now());
                    commonCheck.setClosed(true);
                    CheckDAO.getInstance().update(commonCheck);
                }

                commonTableUpdateTool.buildOrdersList(table);

            } catch (PrinterException ex) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Error while printing check: " + ex.getMessage()));
            }
        }
    }
}