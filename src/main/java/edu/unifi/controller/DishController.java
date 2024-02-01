package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.TypeOfCourse;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.view.DishUpdateTool;
import edu.unifi.view.DishView;
import org.apache.maven.shared.utils.StringUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class DishController {
    private static List<Dish> dishes;

    public List<Dish> getFilteredDishes(String filter) {
        if (dishes == null || dishes.isEmpty())
            dishes = DishDAO.getInstance().getAll();
        if (filter == null || filter.isEmpty())
            return dishes;
        return dishes.stream().filter(dish -> dish.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class DishEditController extends Observable implements ActionListener {
        private final Dish dish;
        private DishUpdateTool dishUpdateTool;

        public DishEditController(Dish dish, DishUpdateTool dishUpdateTool) {
            this.dish = dish;
            this.dishUpdateTool = dishUpdateTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String dishName = dishUpdateTool.getNameTextField().getText();
            if (StringUtils.isBlank(dishName)) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Dish name cannot be empty"));
                return;
            }
            dish.setName(dishName);

            String priceString = null;
            Integer price = 0;

            try {
                priceString = dishUpdateTool.getPriceTextField().getText();
                String[] decimals = priceString.split("\\.");
                if(decimals.length < 2 || decimals[1].length() > 2){
                    throw new NumberFormatException();
                }
                price = Integer.parseInt(dishUpdateTool.getPriceTextField().getText().replace(".",""));
            }catch(NumberFormatException ex) {

                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "The price must be in \nthe format intPrice.xx"));
                return;
            }

            dish.setPrice(price);
            dish.setDescription(dishUpdateTool.getDescriptionLabel().getText());

            TypeOfCourse typeOfCourse = new TypeOfCourse();
            typeOfCourse.setName(Objects.requireNonNull(dishUpdateTool.getTypeComboBox().getSelectedItem()).toString());
            dish.setTypeOfCourse((typeOfCourse));

            DishDAO.getInstance().update(dish);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.UPDATE_DISH, dish.getName() + " updated successfully"));
            dishUpdateTool.dispose();
        }
    }

    public static class DishDeletionController extends Observable implements ActionListener{

        private final Dish dish;
        public DishDeletionController(Dish dish){

            this.dish = dish;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dishes.remove(dish);
            if (dishes.isEmpty())
                dishes = null;
            DishDAO.getInstance().delete(dish);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.DELETE_DISH, dish.getName() + " deleted successfully"));

        }
    }
    public void setDishesToNull(){dishes = null;}
}
