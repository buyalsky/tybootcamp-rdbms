package com.tybootcamp.ecomm.services;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.tybootcamp.ecomm.entities.Seller;
import com.tybootcamp.ecomm.enums.Gender;
import com.tybootcamp.ecomm.repositories.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SellerServiceTest {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerRepository sellerRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private Seller seller;
    @BeforeEach
    void setUp() {
        Name name = faker.name();
        seller = new Seller();
        seller.setFirstName(name.firstName());
        seller.setLastName(name.lastName());
        seller.setAccountId(name.fullName());
        seller.setBirthday(faker.date().birthday());
        seller.setGender(random.nextFloat() < 0.5 ? Gender.Female : Gender.Male);
    }

    @Test
    void getSellerById() {
        Seller savedSeller = sellerRepository.save(seller);
        Seller foundSeller = sellerService.getSellerById(savedSeller.getId());

        assertEquals(savedSeller, foundSeller);
    }

    @Test
    void createSeller() {
        Seller savedSeller = sellerService.createSeller(seller);
        Seller foundSeller = sellerRepository.findById(savedSeller.getId())
                .orElseThrow();

        assertEquals(savedSeller, foundSeller);
    }

    @Test
    void deleteSeller() {
        seller = sellerService.createSeller(seller);
        Long sellerId = seller.getId();
        sellerService.deleteSeller(sellerId);
        assertThrows(EntityNotFoundException.class,
                () -> sellerService.getSellerById(sellerId));
    }

    @Test
    void updateSeller() {
        Seller createdSeller = sellerRepository.save(seller);
        Name updatedName = faker.name();
        Address updatedAddress = faker.address();

        createdSeller.setFirstName(updatedName.firstName());
        createdSeller.setLastName(updatedName.lastName());
        createdSeller.setAddress(updatedAddress.fullAddress());

        Seller updatedSeller = sellerService.updateSeller(createdSeller);

        assertEquals(createdSeller, updatedSeller);
    }
}
