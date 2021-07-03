package com.tybootcamp.ecomm.controllers;

import com.tybootcamp.ecomm.entities.ShoppingCart;
import com.tybootcamp.ecomm.services.ShoppingCartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService service;

    public ShoppingCartController(ShoppingCartService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ShoppingCart addItemToCart(@RequestParam Long customerId,
                                      @RequestParam Long productId,
                                      @RequestParam int quantity) {
        return service.addItemToCart(customerId, productId, quantity);
    }

    @PostMapping("/remove")
    public ShoppingCart removeItemFromCart(@RequestParam Long customerId,
                                           @RequestParam Long productId,
                                           @RequestParam int quantityToDecrement) {
        return service.removeItemFromCart(customerId, productId, quantityToDecrement);
    }

}
