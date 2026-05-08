package com.factorit.ecommerce.service;

import com.factorit.ecommerce.repository.CartRepository;
import com.factorit.ecommerce.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private final CartRepository cartRepository;
    private final PurchaseRepository purchaseRepository;

    public CheckoutService(CartRepository cartRepository, PurchaseRepository purchaseRepository) {
        this.cartRepository = cartRepository;
        this.purchaseRepository = purchaseRepository;
    }


}
