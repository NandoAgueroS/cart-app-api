package com.cart_app.cart_service.exception;

public class CartNotFoundException extends Exception{
    private final Long resourceId;

    public CartNotFoundException(String message) {
        super(message);
        this.resourceId = null;
    }

    public CartNotFoundException(String message, Long resourceId) {
        super(String.format("%s : No such cart with id %d", message, resourceId));
        this.resourceId = resourceId;
    }
}
