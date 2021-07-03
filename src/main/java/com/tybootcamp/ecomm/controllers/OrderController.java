package com.tybootcamp.ecomm.controllers;

import com.tybootcamp.ecomm.entities.Order;
import com.tybootcamp.ecomm.services.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class OrderController {
    private final OrderService checkoutService;

    public OrderController(OrderService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/place-order")
    public Order placeOrder(Long customerId) {
        return checkoutService.placeOrder(customerId);
    }
}
