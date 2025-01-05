package com.cart_app.cart_service.controller;

import com.cart_app.cart_service.dto.CartDetailedResponse;
import com.cart_app.cart_service.dto.CartItemRequest;
import com.cart_app.cart_service.dto.CartResponse;
import com.cart_app.cart_service.exception.CartNotFoundException;
import com.cart_app.cart_service.exception.InvalidArgumentException;
import com.cart_app.cart_service.exception.ProductNotFoundException;
import com.cart_app.cart_service.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestHeader("User-Id-Personalized") String userId) throws CartNotFoundException{
        CartResponse cartResponse = cartService.getBasicCart(userId);
        return ResponseEntity.ok(cartResponse);
    }
    @GetMapping("/detailed")
    public ResponseEntity<CartDetailedResponse> getDetailedCart(@RequestHeader("User-Id-Personalized") String userId) throws CartNotFoundException{
        CartDetailedResponse cartResponse = cartService.getDetailedCart(userId);
        return ResponseEntity.ok(cartResponse);
    }
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getCartTotal(@RequestHeader("User-Id-Personalized") String userId) throws CartNotFoundException{
        BigDecimal total = cartService.getCartTotalPrice(userId);
        return ResponseEntity.ok(total);
    }

    @PostMapping
    public CartResponse createCart(@RequestHeader("User-Id-Personalized") String userId)throws InvalidArgumentException {
        return cartService.createCart(userId);
    }

    @PatchMapping("/remove-item/{productId}")
    public CartResponse removeItem(@RequestHeader("User-Id-Personalized") String userId, @PathVariable Long productId) throws CartNotFoundException, ProductNotFoundException {
        return cartService.removeItemFromCart(userId, productId);
    }

    @PatchMapping("/add-item")
    public CartResponse addItem(@RequestHeader("User-Id-Personalized") String userId, @RequestBody CartItemRequest itemRequest) throws CartNotFoundException, ProductNotFoundException, InvalidArgumentException {
        return cartService.addItemToCart(userId, itemRequest);
    }
}
