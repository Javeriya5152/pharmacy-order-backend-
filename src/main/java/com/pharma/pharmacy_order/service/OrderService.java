package com.pharma.pharmacy_order.service;

import com.pharma.pharmacy_order.model.*;
import com.pharma.pharmacy_order.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MedicineRepository medicineRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order getOrCreateDraftOrder(String category) {
        return orderRepository.findByStatusAndCategory("DRAFT", category)
                .orElseGet(() -> {
                    Order order = new Order();
                    order.setCategory(category);
                    order.setSupplierName(category + " Supplier");
                    order.setStatus("DRAFT");
                    return orderRepository.save(order);
                });
    }

    public Order addItemToOrder(String category, Long medicineId, Integer quantity) {
        Order order = getOrCreateDraftOrder(category);
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        // Check if medicine already exists in order, update qty instead
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        for (OrderItem existing : items) {
            if (existing.getMedicine().getId().equals(medicineId)) {
                existing.setQuantity(existing.getQuantity() + quantity);
                orderItemRepository.save(existing);
                return getOrderById(order.getId());
            }
        }

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setMedicine(medicine);
        item.setQuantity(quantity);
        item.setPriceAtOrder(medicine.getPrice());
        orderItemRepository.save(item);

        return getOrderById(order.getId());
    }

    public Order updateItemQuantity(Long itemId, Integer quantity) {
        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setQuantity(quantity);
        orderItemRepository.save(item);
        return getOrderById(item.getOrder().getId());
    }

    public void removeItemFromOrder(Long itemId) {
        orderItemRepository.deleteById(itemId);
    }

    public Order confirmOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
