package com.tybootcamp.ecomm.controllers;

import com.tybootcamp.ecomm.entities.Category;
import com.tybootcamp.ecomm.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping(path = "/")
    public Iterable<Category> getCategoryByName(@RequestParam(value = "name") String name) {
        return service.getCategoryByName(name);
    }

    @PostMapping(path = "/")
    public Object addNewCategory(@RequestParam(value = "name") String name) {
        return service.addNewCategory(name);
    }

    @PutMapping(path = "/")
    public Category updateCategory(@Valid @RequestBody Category category) {
        return service.updateCategory(category);
    }
}
