package edu.unifi.controller;

import edu.unifi.model.entities.Dish;
import edu.unifi.model.orm.dao.DishDAO;

import java.util.List;

public class OrderController {

    private static List<Dish> dishes;

    public List<Dish> getFilteredDishes(String filter) {
        dishes = DishDAO.getInstance().getAll();
        return dishes.stream().filter(dish -> dish.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class OrderCreationController{

    }
}
