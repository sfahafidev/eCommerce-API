package com.factorit.ecommerce.repository;

import com.factorit.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByDni(String dni);

}
