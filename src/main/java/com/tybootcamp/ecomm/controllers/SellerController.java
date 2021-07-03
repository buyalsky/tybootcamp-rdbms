package com.tybootcamp.ecomm.controllers;

import com.tybootcamp.ecomm.entities.Seller;
import com.tybootcamp.ecomm.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/seller")
public class SellerController {
    private final SellerService service;

    @Autowired
    public SellerController(SellerService service) {
        this.service = service;
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> getSellerById(@RequestParam(value = "id") long id)
    {
        Seller sellerEntity = service.getSellerById(id);
        return new ResponseEntity<>(sellerEntity, HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Seller> addNewSeller(@Valid @RequestBody Seller seller) {
        Seller sellerEntity = service.createSeller(seller);
        return new ResponseEntity<>(sellerEntity, HttpStatus.OK);
    }

    @PutMapping(path = "/")
    public ResponseEntity<Seller> updateSeller(@Valid @RequestBody Seller seller)
    {
        Seller sellerEntity = service.updateSeller(seller);
        return new ResponseEntity<>(sellerEntity, HttpStatus.OK);
    }
}
