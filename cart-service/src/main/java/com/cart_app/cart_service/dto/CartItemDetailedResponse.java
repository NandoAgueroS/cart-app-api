package com.cart_app.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDetailedResponse {
    private Integer quantity;
    private BigDecimal totalProduct;
    private Long productId;
    private ProductDTO product;
}
