package edu.unifi.view;

import edu.unifi.Notifier;
import edu.unifi.controller.DishToolController;
import edu.unifi.model.entities.Dish;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.event.ActionListener;

public class DishUpdateTool extends DishCreationTool {

    private Dish dish;
    private static volatile DishUpdateTool instance;

    private DishUpdateTool( Dish dish, int width, int height) throws Exception {
        super("Dish Update Tool",width,height);
        this.dish = dish;

        JButton updateButton = getCreateButton();
        updateButton.setText("Update");
        updateButton.setIcon(FontIcon.of(MaterialDesignP.PENCIL, 20));

        ActionListener[] actionListeners = updateButton.getActionListeners();
        for (ActionListener a : actionListeners)
            updateButton.removeActionListener(a);

        getTitleLabel().setText("Dish Update Tool");

        getNameField().setText(dish.getName());

        String priceString = dish.getPrice().toString();
        String intString = priceString.substring(0,priceString.length()-2);
        String decimalString = priceString.substring(priceString.length()-2);

        getPriceTextField().setText(intString+"."+decimalString);

        getDescriptionTextArea().setText(dish.getDescription());

        getTypeComboBox().setSelectedItem(dish.getTypeOfCourse().getName());

        DishToolController.DishEditController dishEditController = new DishToolController.DishEditController(dish,this);

        Notifier notifier = Notifier.getInstance();

        dishEditController.addObserver(notifier);
        updateButton.addActionListener(dishEditController);

        setName("Dish Update Tool");
    }

    public static DishUpdateTool getInstance(String title, Dish d, int width, int height) throws Exception {
        DishUpdateTool thisInstance = instance;
        if (instance == null) {
            synchronized (DishUpdateTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new DishUpdateTool(d, width, height);
            }
        }
        return thisInstance;
    }

    //to "reset" the singleton
    @Override
    public void dispose() {
        instance = null;
        super.dispose();
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
