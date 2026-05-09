package com.factorit.ecommerce.controller;

import com.factorit.ecommerce.dto.PurchaseDto;
import com.factorit.ecommerce.service.PurchaseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<PurchaseDto> getAllPurchases() {
        return purchaseService.findAll();
    }

    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<PurchaseDto> checkout(@PathVariable Long cartId) {
        PurchaseDto purchase = purchaseService.checkout(cartId);
        return ResponseEntity.ok(purchase);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<List<PurchaseDto>> getPurchasesByDni(@PathVariable String dni) {
        List<PurchaseDto> purchases = purchaseService.findByDni(dni);
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/dni/{dni}/range")
    public ResponseEntity<List<PurchaseDto>> getPurchasesByDniAndDateRange(
            @PathVariable String dni,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to) {

        List<PurchaseDto> purchases = purchaseService.findByDniAndDateRange(dni, from, to);

        return ResponseEntity.ok(purchases);
    }

}
