package com.tybootcamp.ecomm.controllers;

import com.tybootcamp.ecomm.entities.Product;
import com.tybootcamp.ecomm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/")
    public Iterable<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @PostMapping(path = "/")
    public Product addNewProduct(@RequestBody Product product)
    {
        return productService.addProduct(product);
    }

    @PutMapping(path = "/")
    public Product updateProduct(@Valid @RequestBody Product product)
    {
        return productService.updateProduct(product);
    }
}
