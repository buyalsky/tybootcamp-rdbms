package com.tybootcamp.ecomm.services;

import com.tybootcamp.ecomm.entities.*;
import com.tybootcamp.ecomm.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class OrderService {
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Transactional
    public Order placeOrder(Long customerId) {
        Customer customer = customerRepository.getById(customerId);
        ShoppingCart shoppingCart = customer.getShoppingCart();

        Set<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingItems();

        Order order = new Order();
        shoppingCartItems.forEach(item -> order.addOrderItem(OrderItem.from(item)));
        order.setCustomer(customer);

        customer.addOrder(order);
        shoppingCart.clear();

        customerRepository.save(customer);
        return order;
    }
}
