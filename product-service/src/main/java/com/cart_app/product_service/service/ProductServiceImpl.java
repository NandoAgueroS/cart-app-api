package com.cart_app.product_service.service;

import com.cart_app.product_service.ProductMapper;
import com.cart_app.product_service.dto.ProductRequest;
import com.cart_app.product_service.dto.ProductResponse;
import com.cart_app.product_service.exception.ProductNotFoundException;
import com.cart_app.product_service.model.ProductEntity;
import com.cart_app.product_service.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    private ProductMapper productMapper = ProductMapper.mapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        ProductEntity productEntity = productMapper.toProductEntity(productRequest);
        System.out.println("---product entity---");
        System.out.println(productRequest);
        System.out.println(productEntity);
        System.out.println("---/product entity---");
        productEntity = productRepository.save(productEntity);
        return productMapper.toProductResponse(productEntity);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) throws ProductNotFoundException {
        if (!productRepository.existsById(id))
            throw new ProductNotFoundException
                    ("Failed to update", id);
        ProductEntity productEntity = productMapper.toProductEntity(productRequest);
        productEntity.setProductId(id);
        productRepository.save(productEntity);
        return productMapper.toProductResponse(productEntity);
    }

    @Override
    public ProductResponse getProductById(Long id) throws ProductNotFoundException {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Cannot get product", id));
        return productMapper.toProductResponse(productEntity);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productEntity ->
                        productMapper.toProductResponse(productEntity))
                .toList();
    }

    @Override
    public List<ProductResponse> getAllProductsById(List<Long> ids) {
        List<ProductEntity> productEntities = productRepository.findAllById(ids);
        return productMapper.toProductResponseList(productEntities);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException{
        if (!productRepository.existsById(id)) throw new ProductNotFoundException("Failed to delete", id);
        productRepository.deleteById(id);
    }
}
