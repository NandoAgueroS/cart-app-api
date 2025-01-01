package com.cart_app.product_service.exception;

public class ProductNotFoundException extends Exception{
    private final Long resourceId;

    public ProductNotFoundException(String message) {
        super(message);
        this.resourceId = null;
    }

    public ProductNotFoundException(String message, Long resourceId) {
        super(String.format("%s : No such product with id %d", message, resourceId));
        this.resourceId = resourceId;
    }
}
