package com.tybootcamp.ecomm.controllers;

import com.tybootcamp.ecomm.entities.Customer;
import com.tybootcamp.ecomm.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping(path = "/")
    public Customer getCustomerById(@RequestParam(value = "id") long id) {
        return service.getCustomerById(id);
    }

    @PostMapping(path = "/")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return service.createCustomer(customer);
    }

    @PutMapping("/")
    public Customer updateCustomer(@Valid @RequestBody Customer customer) {
        return service.updateCustomer(customer);
    }

    @DeleteMapping("/")
    public void removeCustomer(@RequestParam(value = "id") long id) {
        service.deleteCustomer(id);
    }
}
