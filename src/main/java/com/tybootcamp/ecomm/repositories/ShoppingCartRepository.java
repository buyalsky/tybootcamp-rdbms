package com.tybootcamp.ecomm.repositories;

import com.tybootcamp.ecomm.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
