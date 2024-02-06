package edu.unifi.model.entities;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Check.class)
    @JoinColumn(name = "check_id")
    private Check check;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Dish.class)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public OrderId(Check checkId, Dish dishId) {
        super();
        this.check = checkId;
        this.dish = dishId;
    }

    public OrderId() {
        super();
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        OrderId orderId = (OrderId) o;
        return getCheck() != null && Objects.equals(getCheck(), orderId.getCheck())
                && getDish() != null && Objects.equals(getDish(), orderId.getDish());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(check, dish);
    }

    public Long getCheckId() {
        return check.getId();
    }
    public Long getDishId() {
        return dish.getId();
    }
}