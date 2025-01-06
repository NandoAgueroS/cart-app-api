package com.cart_app.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedProductResponse {
    private List<ProductResponse> productResponses;

    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
}
