package com.tybootcamp.ecomm.repositories;

import com.tybootcamp.ecomm.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
