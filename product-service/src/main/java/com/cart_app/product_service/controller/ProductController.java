package com.cart_app.product_service.controller;

import com.cart_app.product_service.dto.PaginatedProductResponse;
import com.cart_app.product_service.dto.ProductRequest;
import com.cart_app.product_service.dto.ProductResponse;
import com.cart_app.product_service.exception.InvalidArgumentException;
import com.cart_app.product_service.exception.ProductNotFoundException;
import com.cart_app.product_service.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page", required = false) Integer page,
                                    @RequestParam(name = "size", required = false) Integer size){
        if (page != null && size != null) {
        System.out.println("Page and size");
        System.out.println(page);
        System.out.println(size);
            Pageable pageable = PageRequest.of(page, size);
            PaginatedProductResponse paginatedProducts = productService.getAllProducts(pageable);
            return ResponseEntity.ok(paginatedProducts);
        }
        List<ProductResponse> productResponse = productService.getAllProducts();
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/paged")
    public PaginatedProductResponse getPaginatedProducts(Pageable pageable){
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findOne(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @GetMapping("/all-by-id")
    public ResponseEntity<List<ProductResponse>> getAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(productService.getAllProductsById(ids));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody ProductRequest productRequest) throws ProductNotFoundException{
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws ProductNotFoundException{
        productService.deleteProduct(id);
    }
}
