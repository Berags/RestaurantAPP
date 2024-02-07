package edu.unifi.controller;

import edu.unifi.Notifier;
import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.TypeOfCourse;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.view.DishCreationTool;
import org.apache.maven.shared.utils.StringUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Observable;

public class DishCreationToolController extends Observable implements ActionListener {
    private final DishCreationTool dishCreationTool;

    public DishCreationToolController(DishCreationTool dishCreationTool) {
        this.dishCreationTool = dishCreationTool;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Dish dish = new Dish();

        //to check if the dish name is empty
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
