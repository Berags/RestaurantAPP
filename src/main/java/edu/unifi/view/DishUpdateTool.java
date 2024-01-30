package edu.unifi.view;

import edu.unifi.controller.DishController;
import edu.unifi.model.entities.Dish;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

public class DishUpdateTool extends DishCreationTool {
    private Dish dish;

    public DishUpdateTool(Dish dish) throws Exception {
        super();
        this.dish = dish;
        getCreateButton().setText("Update");
        getCreateButton().setIcon(FontIcon.of(MaterialDesignP.PENCIL, 20));
        getCreateButton().addActionListener(new DishController.DishEditController(dish));
        getTitleLabel().setText("Dish Update Tool");

        getNameField().setText(dish.getName());
        getPriceSpinner().setValue(dish.getPrice());
        getDescriptionTextArea().setText(dish.getDescription());
        getTypeComboBox().setSelectedItem(dish.getTypeOfCourse());
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
