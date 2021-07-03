package com.tybootcamp.ecomm.services;

import com.tybootcamp.ecomm.entities.Seller;
import com.tybootcamp.ecomm.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class SellerService {
    private final SellerRepository repository;

    @Autowired
    public SellerService(SellerRepository repository) {
        this.repository = repository;
    }

    public Seller getSellerById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Seller createSeller(Seller seller){
        return repository.save(seller);
    }

    public void deleteSeller(Long sellerId) {
        Seller seller = getSellerById(sellerId);
        repository.delete(seller);
    }

    public Seller updateSeller(Seller newSeller) {
        Long sellerId = newSeller.getId();
        Optional<Seller> currentSeller = repository.findById(sellerId);
        if (currentSeller.isEmpty())
            throw new EntityNotFoundException("Specified seller does not exist and cannot be updated");

        return repository.save(newSeller);
    }
}
