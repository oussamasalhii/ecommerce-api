package org.example.ecommerce.dao.web;



import org.example.ecommerce.dao.entities.Order;
import org.example.ecommerce.dao.entities.OrderStatus;
import org.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Créer une commande à partir d’un panier
    @PostMapping("/create")
    public Order createOrder(@RequestParam Long cartId, @RequestParam Long customerId) {
        return orderService.createOrderFromCart(cartId, customerId);
    }

    // Obtenir toutes les commandes
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Obtenir une commande spécifique
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Modifier le statut d’une commande
    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return orderService.updateOrderStatus(id, status);
    }
}

