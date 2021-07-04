package com.tybootcamp.ecomm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ShoppingItem> shoppingItems = new HashSet<>();

    @Transient
    private double totalAmount;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(Set<ShoppingItem> shoppingItems) {
        this.shoppingItems.clear();
        if (shoppingItems != null)
            this.shoppingItems.addAll(shoppingItems);
    }

    public double getTotalAmount() {
        return shoppingItems.stream()
                .mapToDouble(ShoppingItem::getAmount)
                .sum();
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void removeCartItem(ShoppingItem shoppingItem) {
        if (shoppingItem == null)
            return;

        this.shoppingItems.remove(shoppingItem);
        shoppingItem.setShoppingCart(null);
    }

    public void clear() {
        this.shoppingItems.forEach(item -> item.setShoppingCart(null));
        this.shoppingItems.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Double.compare(that.totalAmount, totalAmount) == 0 && Objects.equals(customer, that.customer) && Objects.equals(shoppingItems, that.shoppingItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, shoppingItems, totalAmount);
    }
}
