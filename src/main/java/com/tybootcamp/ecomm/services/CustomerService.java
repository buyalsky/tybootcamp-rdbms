package com.tybootcamp.ecomm.services;

import com.tybootcamp.ecomm.entities.Customer;
import com.tybootcamp.ecomm.entities.ShoppingCart;
import com.tybootcamp.ecomm.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer getCustomerById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Customer createCustomer(Customer customer) {
        customer.setShoppingCart(new ShoppingCart());
        return repository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = getCustomerById(customerId);
    }

    public Customer updateCustomer(Customer newCustomer) {
        Long customerId = newCustomer.getId();
        Optional<Customer> currentCustomer = repository.findById(customerId);
        if (currentCustomer.isEmpty())
            throw new EntityNotFoundException("Specified customer cannot be updated since it does not exist");

        return repository.save(newCustomer);
    }


}
