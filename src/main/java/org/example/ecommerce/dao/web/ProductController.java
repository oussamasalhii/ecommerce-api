package org.example.ecommerce.dao.web;



import org.example.ecommerce.dao.entities.Category;
import org.example.ecommerce.dao.entities.Product;
import org.example.ecommerce.dao.repository.CategoryRepository;
import org.example.ecommerce.dao.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        // Récupérer l'ID de la catégorie envoyée
        Long categoryId = product.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Associer la vraie catégorie complète
        product.setCategory(category);

        return productRepository.save(product);
    }

}
