package org.example.ecommerce.dao.repository;

import org.example.ecommerce.dao.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
