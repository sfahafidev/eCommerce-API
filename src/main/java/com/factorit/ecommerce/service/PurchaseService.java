package com.factorit.ecommerce.service;

import com.factorit.ecommerce.dto.PurchaseDto;
import com.factorit.ecommerce.dto.PurchaseItemDto;
import com.factorit.ecommerce.exceptions.CartExceptions;
import com.factorit.ecommerce.model.*;
import com.factorit.ecommerce.repository.CartRepository;
import com.factorit.ecommerce.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {

    private final CartRepository cartRepository;
    private final PurchaseRepository purchaseRepository;
    private final UtilsService utilsService;

    public PurchaseService(CartRepository cartRepository, PurchaseRepository purchaseRepository, UtilsService utilsService) {
        this.cartRepository = cartRepository;
        this.purchaseRepository = purchaseRepository;
        this.utilsService = utilsService;
    }

    public PurchaseDto checkout(Long cartId){
        Cart cart = utilsService.findCart(cartRepository, cartId);

        Purchase purchase = new Purchase();
        purchase.setDni(cart.getDni());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for(CartItem cartItem : cart.getItems()){
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setPurchase(purchase);
            purchaseItem.setProduct(cartItem.getProduct());
            purchaseItem.setQuantity(cartItem.getQuantity());
            purchaseItem.setPrice(cartItem.getProduct().getPrice());

            purchase.getItems().add(purchaseItem);

            totalAmount = totalAmount.add(purchaseItem.getPrice()
                    .multiply(BigDecimal.valueOf(purchaseItem.getQuantity())));
        }

        // Aplicar lógica de descuentos
        totalAmount = utilsService.applyCartSizeDiscount(cart, totalAmount);
        totalAmount = utilsService.applyFourByThreeDiscount(cart, totalAmount);
        totalAmount = utilsService.applyVipDiscount(cart.getDni(), totalAmount, purchaseRepository);

        purchase.setTotalAmount(totalAmount);

        cart.setStatus(CartStatus.CLOSED);
        cart.setDateUpdated(LocalDate.now());
        cartRepository.save(cart);

        purchaseRepository.save(purchase);

        return toDto(purchase);

    }

    public List<PurchaseDto> findAll() {
        return purchaseRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<PurchaseDto> findByDni(String dni) {
        List<Purchase> purchases = purchaseRepository.findByDni(dni)
                .orElseThrow(() -> new CartExceptions("El DNI indicado no existe o no realizo compras!"));

        return purchases.stream()
                .map(this::toDto)
                .toList();
    }

    public List<PurchaseDto> findByDniAndDateRange(String dni, LocalDate from, LocalDate to){
        List<Purchase> purchases = purchaseRepository.findByDniAndDateBetween(dni, from, to)
                .orElseThrow(() -> new CartExceptions("El DNI indicado no existe o no tiene compras en esa fecha!"));

        return purchases.stream()
                .map(this::toDto)
                .toList();
    }


    public PurchaseDto toDto(Purchase purchase) {
        PurchaseDto dto = new PurchaseDto();
        dto.setId(purchase.getId());
        dto.setDni(purchase.getDni());
        dto.setDate(purchase.getDate());
        dto.setTotalAmount(purchase.getTotalAmount());

        List<PurchaseItemDto> items = purchase.getItems().stream()
                .map(item -> {
                    PurchaseItemDto iDto = new PurchaseItemDto();
                    iDto.setId(item.getId());
                    iDto.setProductName(item.getProduct().getName());
                    iDto.setPrice(item.getPrice());
                    iDto.setQuantity(item.getQuantity());
                    return iDto;
                })
                .toList();

        dto.setItems(items);
        return dto;
    }

}
