package com.tybootcamp.ecomm.repositories;

import com.tybootcamp.ecomm.entities.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingItem, Long> {
}
