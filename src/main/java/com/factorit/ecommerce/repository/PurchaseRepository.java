package com.factorit.ecommerce.repository;

import com.factorit.ecommerce.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByDni(String dni);

    List<Purchase> findByDniAndDateBetween(String dni, LocalDateTime from, LocalDateTime to);

}
