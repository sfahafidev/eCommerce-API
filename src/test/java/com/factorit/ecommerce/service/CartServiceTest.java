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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UtilsService utilsService;

    @InjectMocks
    private CartService cartService;

    @Test
    void createCartWithItem(){
        CartDto dto = new CartDto();
        dto.setDni("12345678");
        dto.setSpecial(true);
        dto.setQuantity(2);
        dto.setItemId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Auriculares");

        Mockito.when(utilsService.findProduct(productRepository, 1L)).thenReturn(product);
        Mockito.when(cartRepository.save(ArgumentMatchers.any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        Cart result = cartService.createCart(dto);

        Assertions.assertEquals("12345678", result.getDni());
        Assertions.assertEquals(1, result.getItems().size());
        Assertions.assertEquals("Auriculares", result.getItems().get(0).getProduct().getName());
    }

    @Test
    void addNewItemToCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus(CartStatus.OPEN);
        cart.setItems(new ArrayList<>());

        Product product = new Product();
        product.setId(2L);
        product.setName("Notebook");

        CartItemDto dto = new CartItemDto();
        dto.setCartId(1L);
        dto.setProductId(2L);
        dto.setQuantity(1);

        Mockito.when(utilsService.findCart(cartRepository, 1L)).thenReturn(cart);
        Mockito.when(utilsService.findProduct(productRepository, 2L)).thenReturn(product);
        Mockito.when(cartRepository.save(ArgumentMatchers.any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        Cart result = cartService.addCartItem(dto);

        Assertions.assertEquals(1, result.getItems().size());
        Assertions.assertEquals("Notebook", result.getItems().get(0).getProduct().getName());
    }

    @Test
    void removeItemQuantity() {
        Product product = new Product();
        product.setId(3L);
        product.setName("Mouse");

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(2);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus(CartStatus.OPEN);
        cart.setItems(new ArrayList<>(List.of(item)));

        CartItemDto dto = new CartItemDto();
        dto.setCartId(1L);
        dto.setProductId(3L);
        dto.setQuantity(1);

        Mockito.when(utilsService.findCart(cartRepository, 1L)).thenReturn(cart);
        Mockito.when(cartRepository.save(ArgumentMatchers.any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        Cart result = cartService.removeCartItem(dto);

        Assertions.assertEquals(1, result.getItems().size());
        Assertions.assertEquals(1, result.getItems().get(0).getQuantity());
    }

    @Test
    void throwExceptionWhenQuantityTooHigh() {
        Product product = new Product();
        product.setId(3L);
        product.setName("Mouse");

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(2);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus(CartStatus.OPEN);
        cart.setItems(new ArrayList<>(List.of(item)));

        CartItemDto dto = new CartItemDto();
        dto.setCartId(1L);
        dto.setProductId(3L);
        dto.setQuantity(5);

        Mockito.when(utilsService.findCart(cartRepository, 1L)).thenReturn(cart);

        Assertions.assertThrows(CartExceptions.class, () -> cartService.removeCartItem(dto));
    }

    @Test
    void deleteCartSuccessfully() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setStatus(CartStatus.OPEN);

        Mockito.when(utilsService.findCart(cartRepository, 1L)).thenReturn(cart);

        cartService.deleteCart(1L);

        Mockito.verify(cartRepository).delete(cart);
    }

}
