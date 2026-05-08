package com.factorit.ecommerce.service;

import com.factorit.ecommerce.dto.CartDto;
import com.factorit.ecommerce.dto.CartItemDto;
import com.factorit.ecommerce.exceptions.CartClosedException;
import com.factorit.ecommerce.model.Cart;
import com.factorit.ecommerce.model.CartItem;
import com.factorit.ecommerce.model.CartStatus;
import com.factorit.ecommerce.model.Product;
import com.factorit.ecommerce.repository.CartRepository;
import com.factorit.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart createCart(CartDto cartDto){
        Cart cart = new Cart();
        cart.setDni(cartDto.getDni());
        cart.setSpecial(cartDto.isSpecial());
        cart.setStatus(CartStatus.OPEN);

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setQuantity(cartDto.getQuantity());

        item.setProduct(findProduct(cartDto.getItemId()));

        cart.getItems().add(item);

        return cartRepository.save(cart);
    }

    public Cart addCartItem(CartItemDto cartItemDto){
        Cart cart = findCart(cartItemDto.getCartId());
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setQuantity(cartItemDto.getQuantity());
        item.setProduct(findProduct(cartItemDto.getProductId()));

        cart.getItems().add(item);
        return cartRepository.save(cart);
    }

    protected Product findProduct(Long id){
        if (id != null){
            return productRepository.findById(id)
                    .orElseThrow(() -> new CartClosedException("Producto no encontrado"));
        }
        return null;
    }

    public Cart removeCartItem(CartItemDto cartItemDto) {
        Cart cart = findCart(cartItemDto.getCartId());

        CartItem itemToRemove = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(cartItemDto.getProductId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item no encontrado en el carrito"));

        cart.getItems().remove(itemToRemove);

        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) throws CartClosedException {
        Cart cart = findCart(id);

        if (cart.getStatus() == CartStatus.CLOSED) {
            throw new CartClosedException("No se puede eliminar un carrito cerrado!");
        }

        cartRepository.delete(cart);
    }

    protected Cart findCart(Long id){
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartClosedException("Carrito no encontrado!"));
    }

    public List<Cart> findAllCarts(){
        return cartRepository.findAll();
    }


}
