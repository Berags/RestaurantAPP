package edu.unifi.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "type_of_course")
public class TypeOfCourse {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "typeOfCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Dish> dishes = new ArrayList<>();

    public Collection<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}