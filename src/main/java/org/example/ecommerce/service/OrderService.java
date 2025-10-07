package org.example.ecommerce.service;

import org.example.ecommerce.dao.entities.*;
import org.example.ecommerce.dao.repository.CartRepository;
import org.example.ecommerce.dao.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    /**
     * Crée une commande à partir d'un panier existant
     */
    public Order createOrderFromCart(Long cartId, Long customerId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Panier introuvable avec id : " + cartId));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Le panier est vide !");
        }

        Order order = new Order();
        order.setCustomerId(customerId);

        double total = 0;

        for (CartItem cartItem : cart.getItems()) {

            // ✅ Vérification : produit non nul
            if (cartItem.getProduct() == null) {
                throw new RuntimeException("Le panier contient un article sans produit (id: " + cartItem.getId() + ")");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            order.addItem(orderItem);

            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        order.setTotalPrice(total);
        order.setStatus(OrderStatus.CONFIRMED);

        // ✅ Nettoyer le panier après commande
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    /**
     * Récupère une commande par ID
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec id : " + id));
    }

    /**
     * Change le statut d'une commande
     */
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    /**
     * Liste toutes les commandes
     */
    public java.util.List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
