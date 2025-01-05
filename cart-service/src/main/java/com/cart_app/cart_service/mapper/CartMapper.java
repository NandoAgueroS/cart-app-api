package com.cart_app.cart_service.mapper;

import com.cart_app.cart_service.dto.CartDetailedResponse;
import com.cart_app.cart_service.dto.CartItemRequest;
import com.cart_app.cart_service.dto.CartResponse;
import com.cart_app.cart_service.model.CartEntity;
import com.cart_app.cart_service.model.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CartItemMapper.class)
public interface CartMapper {
    CartMapper mapper = Mappers.getMapper(CartMapper.class);

    CartResponse entityToResponse(CartEntity cartEntity);

    @Mapping(source = "itemList", target = "itemDetailedList")
    CartDetailedResponse entityToDetailedResponse(CartEntity cartEntity);

}
