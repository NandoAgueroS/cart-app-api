package com.cart_app.cart_service.service;

import com.cart_app.cart_service.dto.CartDetailedResponse;
import com.cart_app.cart_service.dto.CartItemRequest;
import com.cart_app.cart_service.dto.CartResponse;
import com.cart_app.cart_service.exception.CartNotFoundException;
import com.cart_app.cart_service.exception.InvalidArgumentException;
import com.cart_app.cart_service.exception.ProductNotFoundException;

import java.math.BigDecimal;

public interface ICartService {

    CartResponse createCart(String userId) throws InvalidArgumentException;

    CartResponse addItemToCart(String userId, CartItemRequest cartItemRequest) throws ProductNotFoundException, CartNotFoundException, InvalidArgumentException;

    CartResponse removeItemFromCart(String userId, Long productId) throws ProductNotFoundException, CartNotFoundException;

    CartDetailedResponse getDetailedCart(String userId)throws CartNotFoundException;

    CartResponse getBasicCart(String userId) throws CartNotFoundException;

    BigDecimal getCartTotalPrice(String userId) throws CartNotFoundException;

}
