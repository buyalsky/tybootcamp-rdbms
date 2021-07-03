package com.tybootcamp.ecomm.services;

import com.tybootcamp.ecomm.entities.Customer;
import com.tybootcamp.ecomm.entities.Product;
import com.tybootcamp.ecomm.entities.ShoppingCart;
import com.tybootcamp.ecomm.entities.ShoppingItem;
import com.tybootcamp.ecomm.repositories.ShoppingCartItemRepository;
import com.tybootcamp.ecomm.repositories.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ShoppingCartService {
    private final CustomerService customerService;
    private final ProductService productService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    public ShoppingCartService(CustomerService customerService,
                               ProductService productService,
                               ShoppingCartRepository shoppingCartRepository,
                               ShoppingCartItemRepository shoppingCartItemRepository) {
        this.customerService = customerService;
        this.productService = productService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    private ShoppingItem popShoppingItemByProductId(ShoppingCart shoppingCart, Long productId) {
        ShoppingItem shoppingItem = shoppingCart.getShoppingItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findAny().orElse(null);

        shoppingCart.removeCartItem(shoppingItem);

        return shoppingItem;
    }

    @Transactional
    public ShoppingCart addItemToCart(Long customerId, Long productId, int quantity) {
        Product product = productService.getProductById(productId);
        Customer customer = customerService.getCustomerById(customerId);
        ShoppingCart shoppingCart = customer.getShoppingCart();

        ShoppingItem shoppingItem = popShoppingItemByProductId(shoppingCart, productId);

        if (shoppingItem == null) {
            shoppingItem = new ShoppingItem();
            shoppingItem.setProduct(product);
            shoppingItem.setQuantity(quantity);
        } else {
            int currentQuantity = shoppingItem.getQuantity();
            shoppingItem.setQuantity(quantity + currentQuantity);
        }
        shoppingItem.setShoppingCart(shoppingCart);
        shoppingCart.getShoppingItems().add(shoppingItem);
        customer.setShoppingCart(shoppingCart);
        customer = customerService.updateCustomer(customer);
        return customer.getShoppingCart();
    }

    public ShoppingCart removeItemFromCart(Long customerId,
                                           Long productId,
                                           int quantityToDecrement) {
        Customer customer = customerService.getCustomerById(customerId);
        ShoppingCart shoppingCart = customer.getShoppingCart();

        ShoppingItem shoppingItem = popShoppingItemByProductId(shoppingCart, productId);
        if (shoppingItem == null) {
            throw new IllegalArgumentException("Cannot decrement the quantity " +
                    "since the specified product has not added to the shopping cart");
        }

        int currentQuantity = shoppingItem.getQuantity();

        // In cases where quantityToDecrement is greater than currentQuantity
        // set newQuantity to 0
        int newQuantity = Math.max(currentQuantity-quantityToDecrement, 0);
        if (newQuantity != 0) {
            shoppingItem.setShoppingCart(shoppingCart);
            shoppingItem.setQuantity(newQuantity);
            shoppingCart.getShoppingItems().add(shoppingItem);
        }

        return shoppingCartRepository.save(shoppingCart);
    }
}
