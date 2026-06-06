package com.pharma.pharmacy_order.controller;

import com.pharma.pharmacy_order.model.Order;
import com.pharma.pharmacy_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAll() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/draft/{category}")
    public Order getDraft(@PathVariable String category) {
        return orderService.getOrCreateDraftOrder(category);
    }

    @PostMapping("/items")
    public Order addItem(@RequestBody Map<String, Object> body) {
        String category = body.get("category").toString();
        Long medicineId = Long.valueOf(body.get("medicineId").toString());
        Integer quantity = Integer.valueOf(body.get("quantity").toString());
        return orderService.addItemToOrder(category, medicineId, quantity);
    }

    @PutMapping("/items/{itemId}")
    public Order updateItem(@PathVariable Long itemId, @RequestBody Map<String, Object> body) {
        Integer quantity = Integer.valueOf(body.get("quantity").toString());
        return orderService.updateItemQuantity(itemId, quantity);
    }

    @DeleteMapping("/items/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        orderService.removeItemFromOrder(itemId);
    }

    @PutMapping("/{id}/confirm")
    public Order confirm(@PathVariable Long id) {
        return orderService.confirmOrder(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}