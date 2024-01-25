package edu.unifi.model.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @EmbeddedId
    private OrderId id = new OrderId();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "check_id")
    @MapsId("checkId")
    private Check check;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dish_id")
    @MapsId("dishId")
    private Dish dish;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public OrderId getId() {
        return id;
    }
}