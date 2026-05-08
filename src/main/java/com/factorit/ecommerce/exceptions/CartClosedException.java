package com.factorit.ecommerce.exceptions;

public class CartClosedException extends RuntimeException{

    public CartClosedException(String message) {
        super(message);
    }

}
