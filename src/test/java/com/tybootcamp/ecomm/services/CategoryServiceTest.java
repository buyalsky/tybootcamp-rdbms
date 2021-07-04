package com.tybootcamp.ecomm.services;

import com.tybootcamp.ecomm.entities.Category;
import com.tybootcamp.ecomm.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void getCategoryByName() {
        String categoryName = "Cell phone";
        Category category = new Category(categoryName);
        categoryRepository.save(category);
        Iterable<Category> foundCategory = categoryService.getCategoryByName(categoryName);
        assertNotNull(foundCategory);
    }

    @Test
    void getCategoryByNameNotExist() {
        String categoryName = "category that not exist";
        assertThrows(EntityNotFoundException.class,
                () -> categoryService.getCategoryByName(categoryName));
    }


    @Test
    void addNewCategory() {
        String categoryName = "Publication";
        categoryService.addNewCategory(categoryName);
        List<Category> foundCategories = categoryRepository.findAllByName(categoryName);
        assertFalse(foundCategories.isEmpty());
    }

    @Test
    void updateCategory() {
        Category savedCategory = categoryRepository.save(new Category("TV"));
        savedCategory.setName("Electronics");
        Category updatedCategory = categoryService.updateCategory(savedCategory);
        assertEquals(savedCategory.getName(), updatedCategory.getName());
    }
}
