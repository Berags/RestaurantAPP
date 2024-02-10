package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.TypeOfCourse;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.model.orm.dao.OrderDAO;
import edu.unifi.view.DishCreationTool;
import edu.unifi.view.DishUpdateTool;
import org.apache.maven.shared.utils.StringUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class DishToolController {
    private static List<Dish> dishes;

    /**
     *
     * @param filter
     * @return the list of dishes currently in the menu and filtered using the filter String
     */

    public List<Dish> getFilteredDishes(String filter) {
        dishes = DishDAO.getInstance().getAll();
        return dishes.stream().filter(dish -> dish.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class DishCreationToolController extends Observable implements ActionListener {
        private final DishCreationTool dishCreationTool;

        public DishCreationToolController(DishCreationTool dishCreationTool) {
            this.dishCreationTool = dishCreationTool;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            Dish dish = new Dish();

            //to check if the dish name is empty
            //TODO: Check if the price is equal to 0.
            String dishName = dishCreationTool.getNameTextField().getText();
            if (StringUtils.isBlank(dishName)) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Dish name cannot be empty"));
                return;
            }
            dish.setName(dishName);

            String priceString = null;
            int price = 0;

            //to assure the correct format of the price with intPart.xx
            try {
                priceString = dishCreationTool.getPriceTextField().getText();
                String[] decimals = priceString.split("\\.");
                if(decimals.length < 2 || decimals[1].length() > 2){
                    throw new NumberFormatException();
                }
                price = Integer.parseInt(dishCreationTool.getPriceTextField().getText().replace(".",""));
            }catch(NumberFormatException ex) {

                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "The price must be in \nthe format intPrice.xx"));
                return;
            }

            dish.setPrice(price);
            dish.setDescription(dishCreationTool.getDescriptionLabel().getText());
            TypeOfCourse typeOfCourse = new TypeOfCourse();
            typeOfCourse.setName(Objects.requireNonNull(dishCreationTool.getTypeComboBox().getSelectedItem()).toString());
            dish.setTypeOfCourse((typeOfCourse));


            DishDAO.getInstance().insert(dish);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.ADD_DISH, dish.getName() + " successfully added!"));
            dishCreationTool.dispose();
        }
    }


    public static class DishEditController extends Observable implements ActionListener {
        private final Dish dish;
        private final DishUpdateTool dishUpdateTool;

        public DishEditController(Dish dish, DishUpdateTool dishUpdateTool) {
            this.dish = dish;
            this.dishUpdateTool = dishUpdateTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String dishName = dishUpdateTool.getNameTextField().getText();

            //if the table hasn't an open check associated
            if(!(OrderDAO.getInstance().getByDishValideCheck(dish.getId())).isEmpty()){
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, dish.getName() +
                        " The dish can't be updated because is part \n of some open orders"));
                return;
            }
            //if the table name inserted is blank
            if (StringUtils.isBlank(dishName)) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Dish name cannot be empty"));
                return;
            }
            dish.setName(dishName);

            String priceString = null;
            int price = 0;

            //to assure the correct format of the price with intPart.xx
            try {
                priceString = dishUpdateTool.getPriceTextField().getText();
                String[] decimals = priceString.split("\\.");
                if (decimals.length < 2 || decimals[1].length() > 2) {
                    throw new NumberFormatException();
                }
                price = Integer.parseInt(dishUpdateTool.getPriceTextField().getText().replace(".", ""));
            } catch (NumberFormatException ex) {

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

    public static class DishDeletionController extends Observable implements ActionListener {
        private final Dish dish;

        public DishDeletionController(Dish dish) {
            this.dish = dish;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            //to check if the dish has been ordinated
            if(!(OrderDAO.getInstance().getByDishValideCheck(dish.getId())).isEmpty()) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, dish.getName() +
                        " can't be deleted because is part of some open orders"));
                return;
            }

            dishes.remove(dish);
            if (dishes.isEmpty())
                dishes = null;
            DishDAO.getInstance().delete(dish);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.DELETE_DISH, dish.getName() + " deleted successfully"));

        }
    }
}
