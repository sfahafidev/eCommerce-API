package com.factorit.ecommerce.repository;

import com.factorit.ecommerce.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
