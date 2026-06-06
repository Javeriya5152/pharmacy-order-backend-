package com.pharma.pharmacy_order.service;

import com.pharma.pharmacy_order.model.Medicine;
import com.pharma.pharmacy_order.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findByAvailableTrue();
    }

    public Medicine addMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public Medicine updateMedicine(Long id, Medicine updated) {
        Medicine existing = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        existing.setUnit(updated.getUnit());
        existing.setPrice(updated.getPrice());
        return medicineRepository.save(existing);
    }

    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }
}