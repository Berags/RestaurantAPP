package edu.unifi.controller;

import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.TypeOfCourse;
import edu.unifi.model.orm.dao.DishDAO;
import edu.unifi.view.DishCreationTool;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class DishCreationToolController extends Observable implements ActionListener {

    private final DishCreationTool dishCreationTool;

    public DishCreationToolController(DishCreationTool dishCreationTool){
        this.dishCreationTool = dishCreationTool;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Dish dish = new Dish();

        String dishName = dishCreationTool.getNameTextField().getText();
        if(!dishName.equals("")){
            dish.setName(dishName);
            dish.setPrice((Integer)(dishCreationTool.getPriceSpinner().getValue()));
            dish.setDescription(dishCreationTool.getDescriptionLabel().getText());
            TypeOfCourse typeOfCourse = new TypeOfCourse();
            typeOfCourse.setName(dishCreationTool.getTypeComboBox().getSelectedItem().toString());
            dish.setTypeOfCourse((typeOfCourse));

            DishDAO.getinstance().insert(dish);
            setChanged();
            notifyObservers(MessageType.ADD_DISH);
            dishCreationTool.dispose();
        }else {
            //TODO: to uniform with other error messages?
            JOptionPane.showMessageDialog(null, "The dish name must not be blank", "Error in the compilation", JOptionPane.ERROR_MESSAGE);
        }

    }
}
