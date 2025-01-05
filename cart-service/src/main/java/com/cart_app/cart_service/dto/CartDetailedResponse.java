package com.cart_app.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailedResponse {

    private BigDecimal totalCart;
    private List<CartItemDetailedResponse> itemDetailedList;
}
