package com.tybootcamp.ecomm.repositories;

import com.tybootcamp.ecomm.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
