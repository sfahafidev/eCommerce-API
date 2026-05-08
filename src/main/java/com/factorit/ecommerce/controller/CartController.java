package com.factorit.ecommerce.controller;

import com.factorit.ecommerce.dto.CartDto;
import com.factorit.ecommerce.dto.CartItemDto;
import com.factorit.ecommerce.model.Cart;
import com.factorit.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getOpenCarts(){ // @RequestParam String dni
        List<Cart> carts = cartService.findAllCarts();

        return ResponseEntity.ok(carts);
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CartDto cartDto){
        Cart cart = cartService.createCart(cartDto);

        return ResponseEntity.ok(cart);
    }

    @PutMapping("/add-item")
    public ResponseEntity<Cart> addItemCart(@RequestBody CartItemDto cartItemDto){
        Cart cart = cartService.addCartItem(cartItemDto);

        return ResponseEntity.ok(cart);
    }

    @PutMapping("/remove-item")
    public ResponseEntity<Cart> removeItemCart(@RequestBody CartItemDto cartItemDto){
        Cart cart = cartService.removeCartItem(cartItemDto);

        return ResponseEntity.ok(cart);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);

        return ResponseEntity.noContent().build();
    }

}
