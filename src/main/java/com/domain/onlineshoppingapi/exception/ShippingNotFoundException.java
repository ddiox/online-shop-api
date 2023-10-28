package com.domain.onlineshoppingapi.exception;

public class ShippingNotFoundException extends RuntimeException {

    public ShippingNotFoundException(String message) {
        super(message);
    }
}
