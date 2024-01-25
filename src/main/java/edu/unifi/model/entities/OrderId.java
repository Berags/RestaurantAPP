package edu.unifi.model.entities;

import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long checkId;
    private Long dishId;

    public OrderId(Long checkId, Long dishId) {
        super();
        this.checkId = checkId;
        this.dishId = dishId;
    }

    public OrderId() {

    }

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return Objects.equals(getCheckId(), orderId.getCheckId()) && Objects.equals(getDishId(), orderId.getDishId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCheckId(), getDishId());
    }
}