package com.cart_app.cart_service.mapper;

import com.cart_app.cart_service.dto.CartItemDetailedResponse;
import com.cart_app.cart_service.dto.CartItemRequest;
import com.cart_app.cart_service.dto.CartItemResponse;
import com.cart_app.cart_service.model.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CartItemMapper {

    CartItemMapper mapper = Mappers.getMapper(CartItemMapper.class);

    CartItemEntity toCartItemEntity(CartItemResponse cartItemResponse);
    CartItemResponse toCartItemResponse(CartItemEntity cartItemEntity);
    CartItemDetailedResponse toCartItemDetailedResponse(CartItemEntity cartItemEntity);
    CartItemEntity toCartItemEntity(CartItemRequest cartItemRequest);
}
