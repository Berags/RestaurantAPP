package edu.unifi.model.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @EmbeddedId
    private OrderId id = new OrderId();

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public Order(OrderId id) {
        this.id = id;
    }
    public Order() {

    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderId getId() {
        return id;
    }

    public void setId(OrderId id) {
        this.id = id;
    }
}