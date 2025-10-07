package org.example.ecommerce.service;

import org.example.ecommerce.dao.entities.Cart;
import org.example.ecommerce.dao.entities.CartItem;
import org.example.ecommerce.dao.entities.Product;
import org.example.ecommerce.dao.repository.CartRepository;
import org.example.ecommerce.dao.repository.ProductRepository;
import org.example.ecommerce.dto.CartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /**
     * Ajouter plusieurs produits dans le panier
     */
    public Cart addMultipleProducts(Long cartId, List<CartItemRequest> items) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Panier introuvable avec id : " + cartId));

        for (CartItemRequest req : items) {
            Product product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produit introuvable avec id : " + req.getProductId()));

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(req.getQuantity());
            cart.addItem(cartItem);
        }

        return cartRepository.save(cart);
    }
}
