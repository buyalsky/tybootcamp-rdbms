package com.tybootcamp.ecomm.services;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.tybootcamp.ecomm.entities.Customer;
import com.tybootcamp.ecomm.entities.ShoppingCart;
import com.tybootcamp.ecomm.enums.Gender;
import com.tybootcamp.ecomm.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        Date birthday = faker.date().birthday();
        Name name = faker.name();
        Address address = faker.address();
        customer.setFirstName(name.firstName());
        customer.setBirthday(birthday);
        customer.setLastName(name.lastName());
        customer.setEmailAddress(String.format("%s@mail.com", name.fullName()));
        customer.setAddress(address.fullAddress());
        customer.setGender(random.nextFloat() < 0.5 ? Gender.Female : Gender.Male);
        customer.setShoppingCart(new ShoppingCart());
    }

    @Test
    void getCustomerById() {
        Customer savedCustomer = customerRepository.save(customer);
        Long savedCustomerId = savedCustomer.getId();
        Customer foundCustomer = customerService.getCustomerById(savedCustomerId);
        assertEquals(savedCustomer, foundCustomer);
    }

    @Test
    void createCustomer() {
        Customer createdCustomer = customerService.createCustomer(this.customer);
        Customer foundCustomer = customerRepository.findById(createdCustomer.getId())
                .orElseThrow();

        assertEquals(createdCustomer, foundCustomer);
    }

    @Test
    void deleteCustomer() {
        Customer createdCustomer = customerRepository.save(this.customer);
        Long customerId = createdCustomer.getId();
        customerService.deleteCustomer(customerId);
        assertThrows(EntityNotFoundException.class,
                () -> customerService.getCustomerById(customerId));
    }

    @Test
    void updateCustomer() {
        Customer createdCustomer = customerRepository.save(customer);
        Name updatedName = faker.name();
        Address updatedAddress = faker.address();

        createdCustomer.setFirstName(updatedName.firstName());
        createdCustomer.setLastName(updatedName.lastName());
        createdCustomer.setAddress(updatedAddress.fullAddress());

        Customer updatedCustomer = customerService.updateCustomer(createdCustomer);
        assertEquals(createdCustomer, updatedCustomer);
    }
}
