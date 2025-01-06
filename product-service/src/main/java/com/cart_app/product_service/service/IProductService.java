package com.cart_app.product_service.service;

import com.cart_app.product_service.dto.ProductRequest;
import com.cart_app.product_service.dto.ProductResponse;
import com.cart_app.product_service.exception.InvalidArgumentException;
import com.cart_app.product_service.exception.ProductNotFoundException;

import java.util.List;

public interface IProductService {
    /**
     * Creates a new product.
     *
     * @param productRequest The information of the product to be created.
     * @return The created product.
     */
    ProductResponse createProduct(ProductRequest productRequest);

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to be updated.
     * @param productRequest The new information of the product.
     * @return The updated product.
     */
    ProductResponse updateProduct(Long id, ProductRequest productRequest) throws ProductNotFoundException;

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product found.
     */
    ProductResponse getProductById(Long id) throws ProductNotFoundException;

    /**
     * Retrieves all products.
     *
     * @return A list of all products.
     */
    List<ProductResponse> getAllProducts();

    /**
     * Retrieves a list of products by their IDs.
     *
     * @param ids a list of product IDs to retrieve.
     * @return a list of products corresponding to the provided product IDs.
     */
    List<ProductResponse> getAllProductsById(List<Long> ids);

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to be deleted.
     */
    void deleteProduct(Long id) throws ProductNotFoundException;
}
