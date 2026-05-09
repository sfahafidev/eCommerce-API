package com.factorit.ecommerce.repository;

import com.factorit.ecommerce.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Optional<List<Purchase>> findByDni(String dni);

    Optional<List<Purchase>> findByDniAndDateBetween(String dni, LocalDate from, LocalDate to);

}
