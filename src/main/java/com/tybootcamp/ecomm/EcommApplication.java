package com.tybootcamp.ecomm;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.tybootcamp.ecomm.entities.*;
import com.tybootcamp.ecomm.enums.Gender;
import com.tybootcamp.ecomm.repositories.CategoryRepository;
import com.tybootcamp.ecomm.repositories.CustomerRepository;
import com.tybootcamp.ecomm.repositories.ProductRepository;
import com.tybootcamp.ecomm.repositories.SellerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

@EnableJpaRepositories(basePackages = "com.tybootcamp.ecomm.repositories")
@SpringBootApplication
public class EcommApplication implements CommandLineRunner {
    private final CategoryRepository _categoryRepository;
    private final ProductRepository _productRepository;
    private final SellerRepository _sellerRepository;
    private final CustomerRepository customerRepository;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public EcommApplication(CategoryRepository _categoryRepository, ProductRepository _productRepository, SellerRepository _sellerRepository, CustomerRepository customerRepository) {
        this._categoryRepository = _categoryRepository;
        this._productRepository = _productRepository;
        this._sellerRepository = _sellerRepository;
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(EcommApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        //--------------Create two sellers-----------------------------------------
        Seller judy = new Seller("Judy", "Adams", Gender.Female, "Judy's account id = 879");
        judy.setBirthday(DATE_FORMAT.parse(("4/12/2010")));
        judy.setEmailAddress("hi@demo.com");
        judy.setId(1L);
        _sellerRepository.save(judy);


        Seller michael = new Seller("Michael", "Martin", Gender.Male, "Micheal's account id = 023");
        michael.setEmailAddress("hi@demo.com");
        michael.setId(2L);
        michael = _sellerRepository.save(michael);

        michael.setFirstName("michael2");
        _sellerRepository.save(michael);


        //--------------Create 4 different categories and save them--------------------
        Category artCategory = new Category("Art");
        Category wallDecorCategory = new Category("Wall Decor");
        Category babyCategory = new Category("Baby");
        Category toysCategory = new Category("Toys");
        artCategory = _categoryRepository.save(artCategory);
        wallDecorCategory = _categoryRepository.save(wallDecorCategory);
        _categoryRepository.save(babyCategory);
        _categoryRepository.save(toysCategory);


        //--------------Create a product in wall decor and art categories--------------
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://c.pxhere.com/photos/b1/ab/fantastic_purple_trees_beautiful_jacaranda_trees_pretoria_johannesburg_south_africa-1049314.jpg!d");
        imageUrls.add("https://c.pxhere.com/photos/90/da/jacaranda_trees_flowering_purple_stand_blossom_spring_plant-922332.jpg!d");

        Seller finalMichael = michael;
        Category finalArtCategory = artCategory;
        Category finalWallDecorCategory = wallDecorCategory;

        IntStream.range(1, 100).parallel().forEach(
                i -> {
                    Book book = new Faker().book();
                    String author = book.author();
                    Product pictureProduct = new Product(author, author,
                            book.title(),
                            42.34, imageUrls, finalMichael, new HashSet<>(Arrays.asList(finalArtCategory, finalWallDecorCategory)));
                    _productRepository.save(pictureProduct);
                }
        );

        Customer customer = new Customer();
        customer.setName("Kyle");
        customer.setBirthday(new Date());
        customer.setLastName("Lowry");
        customer.setEmailAddress("kyle@mail.com");
        customer.setAddress("Toronto");
        customer.setGender(Gender.Male);
        customer.setShoppingCart(new ShoppingCart());
        customerRepository.save(customer);
    }
}
