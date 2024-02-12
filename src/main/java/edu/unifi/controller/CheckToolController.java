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

public class CheckToolController {

    private static TableUpdateTool commonTableUpdateTool;
    private static Table commonTable;
    private static Check commonCheck;

    public static class CheckCreationController extends Observable implements ActionListener {

        public CheckCreationController(Table table) {
            commonTable = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!java.util.Objects.isNull(CheckDAO.getInstance().getValidCheckByTable(commonTable))) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "This table already has an associated check.\n " +
                        "You can clean it using the dedicated button"));
                return;
            }

            commonTable = TableDAO.getInstance().getById(commonTable.getId());
            commonCheck = new Check();
            //we set the issue date with the creation date, then we update it during the printing of the check
            commonCheck.setIssueDate(java.time.LocalDateTime.now());
            commonCheck.setTable(commonTable);
            commonTable.getChecks().add(commonCheck);
            CheckDAO.getInstance().insert(commonCheck);

            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ADD_DISH, "New check created successfully"));

        }
    }

    public static class CheckResetController extends Observable implements ActionListener {

        public CheckResetController(TableUpdateTool tableUpdateTool, Table table) {

            commonTableUpdateTool = tableUpdateTool;
            commonTable = table;

        }
        @Override
        public void actionPerformed(ActionEvent e) {

            if (java.util.Objects.isNull(CheckDAO.getInstance().getValidCheckByTable(commonTable))) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "There isn't any check to delete!"));
                return;
            }

            //to delete all the orders present in the check
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

    public static class PrintCheckController extends Observable implements ActionListener {
        private final Table table;

        public PrintCheckController(Table table) {
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (java.util.Objects.isNull(CheckDAO.getInstance().getValidCheckByTable(commonTable)) ||
                commonTableUpdateTool.getOrders().isEmpty()) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "This table hasn't an associated check " +
                        "\nto print yet or the check is empty"));
                return;
            }

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
