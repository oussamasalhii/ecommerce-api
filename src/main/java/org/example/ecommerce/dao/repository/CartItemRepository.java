package org.example.ecommerce.dao.repository;

import org.example.ecommerce.dao.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}