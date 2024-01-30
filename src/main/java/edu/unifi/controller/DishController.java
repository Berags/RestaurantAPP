package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.TypeOfCourse;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.model.orm.dao.TypeOfCourseDAO;
import edu.unifi.view.DishUpdateTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

public class DishController {
    private List<Dish> dishes;

    public List<Dish> getFilteredDishes(String filter) {
        if (dishes == null)
            dishes = DishDAO.getInstance().getAll();
        if (filter == null || filter.isEmpty())
            return dishes;
        return dishes.stream().filter(dish -> dish.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class DishEditController extends Observable implements ActionListener {
        private final Dish dish;
        private DishUpdateTool dishUpdateTool;

        public DishEditController(Dish dish) {
            this.dish = dish;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Update the logic
            try {
                dish.setName(dishUpdateTool.getNameTextField().getText());
                dish.setPrice((int) dishUpdateTool.getPriceSpinner().getValue());
                dish.setDescription(dishUpdateTool.getDescriptionTextArea().getText());
                DishDAO.getInstance().update(dish);
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.UPDATE_DISH, dish.getName() + " updated successfully"));
            } catch (Exception exception) {
            }
        }
    }
}
