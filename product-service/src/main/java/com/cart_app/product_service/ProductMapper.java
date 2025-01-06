package com.cart_app.product_service;

import com.cart_app.product_service.dto.ProductRequest;
import com.cart_app.product_service.dto.ProductResponse;
import com.cart_app.product_service.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", source = "productId")
    ProductResponse toProductResponse(ProductEntity productEntity);

    List<ProductResponse> toProductResponseList(List<ProductEntity> productEntities);

    ProductEntity toProductEntity(ProductRequest productRequest);
}
