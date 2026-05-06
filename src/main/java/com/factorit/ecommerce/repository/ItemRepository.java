package com.factorit.ecommerce.repository;

import com.factorit.ecommerce.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
