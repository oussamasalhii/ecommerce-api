package org.example.ecommerce.dao.repository;




import org.example.ecommerce.dao.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
