package org.example.ecommerce.dao.web;

import org.example.ecommerce.dao.entities.Cart;
import org.example.ecommerce.dao.entities.CartItem;
import org.example.ecommerce.dao.repository.CartRepository;
import org.example.ecommerce.dao.repository.CartItemRepository;
import org.example.ecommerce.dto.CartItemRequest;
import org.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final CartService cartService;

    @Autowired
    public CartController(CartRepository cartRepository,
                          CartItemRepository cartItemRepository,
                          CartService cartService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
    }
    // 1️⃣ Créer un panier vide
    @PostMapping
    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    // 2️⃣ Récupérer un panier par ID
    @GetMapping("/{id}")
    public Optional<Cart> getCart(@PathVariable Long id) {
        return cartRepository.findById(id);
    }

    // 3️⃣ Ajouter un produit au panier
    @PostMapping("/{cartId}/items")
    public Cart addItemToCart(@PathVariable Long cartId, @RequestBody CartItem item) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        item.setCart(cart);
        cartItemRepository.save(item);
        return cartRepository.findById(cartId).get();
    }

    // 4️⃣ Supprimer un item du panier
    @DeleteMapping("/{cartId}/items/{itemId}")
    public Cart removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartItemRepository.deleteById(itemId);
        return cartRepository.findById(cartId).orElseThrow();
    }

    // 5️⃣ Lister tous les paniers
    @GetMapping
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @PostMapping("/{cartId}/items/bulk")
    public Cart addMultipleItemsToCart(
            @PathVariable Long cartId,
            @RequestBody List<CartItemRequest> items) {
        return cartService.addMultipleProducts(cartId, items);
    }

    @PostMapping("/{cartId}/items/multiple")
    public ResponseEntity<Cart> addMultipleItems(@PathVariable Long cartId,
                                                 @RequestBody List<CartItemRequest> items) {
        return ResponseEntity.ok(cartService.addMultipleProducts(cartId, items));
    }


}
