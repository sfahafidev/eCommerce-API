package com.factorit.ecommerce.controller;

import com.factorit.ecommerce.exceptions.CartExceptions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartExceptions.class)
    public ResponseEntity<Map<String, String>> handleCartException(CartExceptions ex, HttpServletRequest request) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("title", "Operación Invalida");
        errorResponse.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorResponse.put("detail", ex.getMessage());
        errorResponse.put("instance", request.getRequestURI());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
