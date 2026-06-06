package com.pharma.pharmacy_order.repository;

import com.pharma.pharmacy_order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatusIgnoreCase(String status);
    List<Order> findAllByOrderByCreatedAtDesc();
    Optional<Order> findByStatusAndCategory(String status, String category);
}
