package com.factorit.ecommerce.service;

import com.factorit.ecommerce.dto.CartDto;
import com.factorit.ecommerce.dto.CartItemDto;
import com.factorit.ecommerce.exceptions.CartExceptions;
import com.factorit.ecommerce.model.Cart;
import com.factorit.ecommerce.model.CartItem;
import com.factorit.ecommerce.model.CartStatus;
import com.factorit.ecommerce.model.Product;
import com.factorit.ecommerce.repository.CartRepository;
import com.factorit.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UtilsService utilsService;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UtilsService utilsService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.utilsService = utilsService;
    }

    public Cart createCart(CartDto cartDto){
        Cart cart = new Cart();
        cart.setDni(cartDto.getDni());
        cart.setSpecial(cartDto.isSpecial());
        cart.setStatus(CartStatus.OPEN);

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setQuantity(cartDto.getQuantity());

        item.setProduct(utilsService.findProduct(productRepository, cartDto.getItemId()));

        cart.getItems().add(item);

        return cartRepository.save(cart);
    }

    public Cart addCartItem(CartItemDto cartItemDto){
        boolean found = false;

        Cart cart = utilsService.findCart(cartRepository, cartItemDto.getCartId());

        Product newProduct = utilsService.findProduct(productRepository, cartItemDto.getProductId());
        for (CartItem cartItem : cart.getItems()){
            if (newProduct.getName().equals(cartItem.getProduct().getName())){
                int newQuantity = cartItem.getQuantity();
                cartItem.setQuantity(newQuantity + cartItemDto.getQuantity());
                found = true;
            }
        }

        if (!found){
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setQuantity(cartItemDto.getQuantity());
            item.setProduct(newProduct);
            cart.getItems().add(item);
        }

        cart.setDateUpdated(LocalDate.now());

        return cartRepository.save(cart);
    }


    public Cart removeCartItem(CartItemDto cartItemDto) {
        Cart cart = utilsService.findCart(cartRepository, cartItemDto.getCartId());

        CartItem itemToRemove = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(cartItemDto.getProductId()))
                .findFirst()
                .orElseThrow(() -> new CartExceptions("Item no encontrado en el carrito"));

        Integer newQuantity = itemToRemove.getQuantity();

        if (itemToRemove.getQuantity() > 0 && cartItemDto.getQuantity() <= itemToRemove.getQuantity()){
            newQuantity = newQuantity - cartItemDto.getQuantity();
            itemToRemove.setQuantity(newQuantity);
        }else if(cartItemDto.getQuantity() > itemToRemove.getQuantity()){
            throw new CartExceptions("La cantidad indicada es mayor a la existente!");
        }

        if (newQuantity == 0){
            cart.getItems().remove(itemToRemove);
        }

        cart.setDateUpdated(LocalDate.now());

        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) throws CartExceptions {
        Cart cart = utilsService.findCart(cartRepository, id);

        cartRepository.delete(cart);
    }

    public List<Cart> findAllCarts(){
        return cartRepository.findAll();
    }


}
