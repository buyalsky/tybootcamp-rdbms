package com.tybootcamp.ecomm.services;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.tybootcamp.ecomm.entities.Category;
import com.tybootcamp.ecomm.entities.Product;
import com.tybootcamp.ecomm.entities.Seller;
import com.tybootcamp.ecomm.enums.Gender;
import com.tybootcamp.ecomm.repositories.CategoryRepository;
import com.tybootcamp.ecomm.repositories.ProductRepository;
import com.tybootcamp.ecomm.repositories.SellerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    private final Faker faker = new Faker();
    private Category firstCategory = new Category("First category");
    private Category secondCategory = new Category("Second category");
    private Seller seller = new Seller("Michael", "Jordan", Gender.Male, "Micheal's account id = 023");
    private final Book book = faker.book();
    private final List<String> imageUrls = Arrays.asList("https://c.pxhere.com/photos/b1/ab/fantastic_purple_trees_beautiful_jacaranda_trees_pretoria_johannesburg_south_africa-1049314.jpg!d",
            "https://c.pxhere.com/photos/90/da/jacaranda_trees_flowering_purple_stand_blossom_spring_plant-922332.jpg!d");
    private Product product = new Product(book.author(), book.title(),42.34, imageUrls, seller, new HashSet<>(Arrays.asList(firstCategory, secondCategory)));;
    private final Product missingProduct = new Product();

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        seller = sellerRepository.save(seller);
        firstCategory = categoryRepository.save(firstCategory);
        secondCategory = categoryRepository.save(secondCategory);
    }

    @Test
    void addProduct() {
        product = this.productService.addProduct(product);
        assertNotNull(product);
    }

    @Test
    void addProductMissing() {
        assertThrows(IllegalArgumentException.class,
                () -> this.productService.addProduct(missingProduct));
    }

    @Test
    void findById() {
        Product createdProduct = productRepository.save(product);
        Product foundProduct = this.productService.getProductById(product.getId());
        assertEquals(createdProduct, foundProduct);
    }

    @Test
    void updateProduct() {
        Product createdProduct = productRepository.save(product);
        String updatedName = book.title();
        String updatedDesc = book.author();
        Double updatedPrice = createdProduct.getPrice() * 2;
        createdProduct.setName(updatedName);
        createdProduct.setDescription(updatedDesc);
        createdProduct.setPrice(updatedPrice);
        Product updatedProduct = productService.updateProduct(createdProduct);
        assertEquals(createdProduct, updatedProduct);
    }

    @Test
    void deleteProduct() {
        Product createdProduct = productRepository.save(product);
        Long productId = createdProduct.getId();
        productService.deleteProduct(productId);
        assertThrows(EntityNotFoundException.class,
                () -> productService.getProductById(productId));
    }
}
