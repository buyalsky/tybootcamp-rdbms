package com.tybootcamp.ecomm.services;

import com.tybootcamp.ecomm.entities.Category;
import com.tybootcamp.ecomm.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Iterable<Category> getCategoryByName(String name) {
        if(name == null){
            return repository.findAll();
        }

        List<Category> categories = repository.findAllByName(name);

        if (categories.isEmpty()) {
            String exceptionMsg = String.format("There isn't any Category with name: %s", name);
            throw new EntityNotFoundException(exceptionMsg);
        }
        return categories;
    }

    public Category addNewCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or null while adding a new category");
        }
        Category createdCategory = new Category(name.trim());
        createdCategory = repository.save(createdCategory);
        return createdCategory;
    }

    public Category updateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Your request is null!");
        }

        repository.findById(category.getId())
                .orElseThrow(EntityNotFoundException::new);

        return repository.save(category);
    }
}
