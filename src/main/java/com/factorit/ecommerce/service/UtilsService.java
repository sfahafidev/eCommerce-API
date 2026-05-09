package com.factorit.ecommerce.service;

import com.factorit.ecommerce.exceptions.CartExceptions;
import com.factorit.ecommerce.model.*;
import com.factorit.ecommerce.repository.CartRepository;
import com.factorit.ecommerce.repository.ProductRepository;
import com.factorit.ecommerce.repository.PurchaseRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Collections;

@Component
public class UtilsService {

    public Cart findCart(CartRepository cartRepository, Long id){
        Cart cart =  cartRepository.findById(id)
                .orElseThrow(() -> new CartExceptions("Carrito no encontrado!"));

        if (cart.getStatus().equals(CartStatus.CLOSED)) {
            throw new CartExceptions("No se puede eliminar un carrito cerrado!");
        }

        return cart;
    }

    public Product findProduct(ProductRepository productRepository, Long id){
        if (id != null){
            return productRepository.findById(id)
                    .orElseThrow(() -> new CartExceptions("Producto no encontrado"));
        }
        return null;
    }

    // Descuento por cantidad de productos (>3)
    public BigDecimal applyCartSizeDiscount(Cart cart, BigDecimal total) {
        int totalProducts = cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        if (totalProducts > 3) {
            if (cart.isSpecial()) {
                total = total.subtract(BigDecimal.valueOf(150));
            } else {
                total = total.subtract(BigDecimal.valueOf(100));
            }
        }
        return total;
    }

    // Promoción 4x3 en productos iguales
    public BigDecimal applyFourByThreeDiscount(Cart cart, BigDecimal total) {
        for (CartItem item : cart.getItems()) {

            if (item.getQuantity() >= 4) {
                int freeUnits = item.getQuantity() / 4;
                BigDecimal discount = item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(freeUnits));
                total = total.subtract(discount);
            }
        }
        return total;
    }

    // Descuento VIP (requiere consultar compras previas)
    public BigDecimal applyVipDiscount(String dni, BigDecimal total, PurchaseRepository purchaseRepository) {
        LocalDateTime now = LocalDateTime.now();
        YearMonth currentMonth = YearMonth.from(now);

        BigDecimal monthlyTotal = purchaseRepository.findByDni(dni)
                .orElse(Collections.emptyList())
                .stream()
                .filter(p -> YearMonth.from(p.getDate()).equals(currentMonth))
                .map(Purchase::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (monthlyTotal.compareTo(BigDecimal.valueOf(5000)) > 0 &&
                total.compareTo(BigDecimal.valueOf(2000)) > 0) {
            total = total.subtract(BigDecimal.valueOf(500));
        }
        return total;
    }

}
