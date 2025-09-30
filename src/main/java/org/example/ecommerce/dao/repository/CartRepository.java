package org.example.ecommerce.dao.repository;

import org.example.ecommerce.dao.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
