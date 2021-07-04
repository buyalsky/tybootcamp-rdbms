package com.tybootcamp.ecomm.services;

import com.github.javafaker.Address;
import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.tybootcamp.ecomm.entities.*;
import com.tybootcamp.ecomm.enums.Gender;
import com.tybootcamp.ecomm.repositories.CategoryRepository;
import com.tybootcamp.ecomm.repositories.CustomerRepository;
import com.tybootcamp.ecomm.repositories.ProductRepository;
import com.tybootcamp.ecomm.repositories.SellerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderAndShoppingCartServiceTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;

    private Category firstCategory = new Category("First category");
    private Category secondCategory = new Category("Second category");
    private final Faker faker = new Faker();
    private final Random random = new Random();
    private Customer customer;
    private static final int NUMBER_OF_PRODUCTS = 5;
    private List<Product> products;


    @BeforeEach
    void setUp() {
        categoryRepository.saveAll(Arrays.asList(firstCategory, secondCategory));
        Name name = faker.name();
        Seller seller = new Seller(name.firstName(),
                name.lastName(),
                random.nextFloat() < 0.5 ? Gender.Female : Gender.Male,
                name.fullName());
        name = faker.name();
        Seller seller2 = new Seller(name.firstName(),
                name.lastName(),
                random.nextFloat() < 0.5 ? Gender.Female : Gender.Male,
                name.fullName());

        seller = sellerRepository.save(seller);
        seller2 = sellerRepository.save(seller2);

        Date birthday = faker.date().birthday();
        name = faker.name();
        Address address = faker.address();
        // TODO: consider using builder to create an object
        customer = new Customer();
        customer.setFirstName(name.firstName());
        customer.setBirthday(birthday);
        customer.setLastName(name.lastName());
        customer.setEmailAddress(String.format("%s@mail.com", name.fullName()));
        customer.setAddress(address.fullAddress());
        customer.setGender(random.nextFloat() < 0.5 ? Gender.Female : Gender.Male);
        products = new ArrayList<>(NUMBER_OF_PRODUCTS);
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {
            Book book = faker.book();
            Product product = new Product(book.title(),
                    book.publisher(),
                    100*random.nextDouble(),
                    Collections.singletonList("https://c.pxhere.com/photos/90/da/jacaranda_trees_flowering_purple_stand_blossom_spring_plant-922332.jpg!d"),
                    random.nextFloat() < 0.5 ? seller : seller2,
                    new HashSet<>(Collections.singleton(random.nextFloat() < 0.5 ? firstCategory : secondCategory)));
            products.add(productRepository.save(product));
        }
        customer.setShoppingCart(new ShoppingCart());
    }

    @Test
    void addItemToCart() {
        customer = customerRepository.save(customer);
        Long customerId = customer.getId();
        Product product = products.get(0);
        Product product2 = products.get(1);
        shoppingCartService.addItemToCart(customerId, product.getId(), 3);
        ShoppingCart finalShoppingCart = shoppingCartService.addItemToCart(customerId, product2.getId(), 5);
        Customer updatedCustomer = customerRepository.findById(customerId).orElseThrow();
        ShoppingCart foundShoppingCart = updatedCustomer.getShoppingCart();
        assertFalse(finalShoppingCart.getShoppingItems().isEmpty());
        assertEquals(finalShoppingCart, foundShoppingCart);
    }

    @Test
    void removeItemFromCart() {
        customer = customerRepository.save(customer);
        Long customerId = customer.getId();
        Product product = products.get(0);
        Product product2 = products.get(1);
        shoppingCartService.addItemToCart(customerId, product.getId(), 3);
        shoppingCartService.addItemToCart(customerId, product2.getId(), 5);
        shoppingCartService.removeItemFromCart(customerId, product.getId(), 2);
        ShoppingCart finalShoppingCart = shoppingCartService.removeItemFromCart(customerId, product2.getId(), 5);

        assertEquals(1, finalShoppingCart.getShoppingItems().size());
        assertEquals(finalShoppingCart.getTotalAmount(), product.getPrice(), 0.01);
    }

    @Test
    void placeOrder() {
        ShoppingCart shoppingCart = new ShoppingCart();
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {
            ShoppingItem shoppingItem = new ShoppingItem();
            shoppingItem.setQuantity(random.nextInt(4));
            shoppingItem.setProduct(products.get(i));
            shoppingCart.getShoppingItems().add(shoppingItem);
        }
        customer.setShoppingCart(shoppingCart);
        customer = customerRepository.save(customer);
        Order order = orderService.placeOrder(customer.getId());
        Customer updatedCustomer = customerRepository.findById(customer.getId()).orElseThrow();
        Set<Order> updatedCustomerOrders = updatedCustomer.getOrders();

        assertTrue(updatedCustomerOrders.contains(order));

        // After placing the order, check if the shopping cart is empty
        ShoppingCart updatedCustomerShoppingCart = updatedCustomer.getShoppingCart();
        assertTrue(updatedCustomerShoppingCart.getShoppingItems().isEmpty());
    }

}
