package com.tybootcamp.ecomm.entities;

import com.tybootcamp.ecomm.enums.Gender;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends Profile {

    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();

    public Customer(){

    }

    public Customer(String firstName, String lastName, Gender gender, ShoppingCart shoppingCart, Set<Order> orders) {
        super(firstName, lastName, gender);
        this.shoppingCart = shoppingCart;
        this.orders = orders;
    }

    public void addOrder(Order order) {
        if (order == null)
            return;
        orders.add(order);
        order.setCustomer(this);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setCustomer(this);
        this.shoppingCart = shoppingCart;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
