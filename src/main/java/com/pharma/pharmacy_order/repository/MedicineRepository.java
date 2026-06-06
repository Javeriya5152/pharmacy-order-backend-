package com.pharma.pharmacy_order.repository;

import com.pharma.pharmacy_order.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByAvailableTrue();
    List<Medicine> findByCategoryIgnoreCase(String category);
}
