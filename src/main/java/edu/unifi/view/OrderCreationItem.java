package edu.unifi.view;

import edu.unifi.model.entities.Dish;

public class OrderCreationItem extends DishItem{

    public OrderCreationItem(Dish dish, int index){
        super(dish);
        getActionTestPanel().remove(getEditList());
        getActionTestPanel().remove(getDeleteButton());
    }
}
