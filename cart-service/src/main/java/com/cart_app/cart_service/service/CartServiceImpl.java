package com.cart_app.cart_service.service;

import com.cart_app.cart_service.dto.*;
import com.cart_app.cart_service.exception.CartNotFoundException;
import com.cart_app.cart_service.exception.InvalidArgumentException;
import com.cart_app.cart_service.exception.ProductNotFoundException;
import com.cart_app.cart_service.feignclient.ProductFeignClient;
import com.cart_app.cart_service.mapper.CartItemMapper;
import com.cart_app.cart_service.mapper.CartMapper;
import com.cart_app.cart_service.model.CartEntity;
import com.cart_app.cart_service.model.CartItemEntity;
import com.cart_app.cart_service.repository.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService{

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private ProductFeignClient productFeignClient;

    private CartMapper cartMapper = CartMapper.mapper;

    private CartItemMapper itemMapper = CartItemMapper.mapper;

    @Override
    public CartResponse createCart(String userId) throws InvalidArgumentException{
        if (userId == null) throw new InvalidArgumentException("The user ID was not provided");
        CartEntity cartEntity = cartRepository.save(
                CartEntity.builder()
                        .userId(userId)
                        .itemList(new ArrayList<>())
                        .build()
        );
        return cartMapper.entityToResponse(cartEntity);
    }

    @Override
    public CartResponse addItemToCart(String userId, CartItemRequest cartItemRequest) throws ProductNotFoundException, CartNotFoundException, InvalidArgumentException{
        ProductDTO productDTO = productFeignClient.findOne(cartItemRequest.getProductId()).getBody();
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseThrow(()-> new CartNotFoundException("Not found"));
        if (productDTO.getStock() < cartItemRequest.getQuantity()) throw new
                InvalidArgumentException(String.format("There is not enough stock for the requested quantity: Available stock: %d", productDTO.getStock()));
        List<CartItemEntity> cartItemEntities = cartEntity.getItemList()
                .stream()
                .filter(item -> cartItemRequest.getProductId().equals(item.getProductId()))
                .toList();
        CartItemEntity cartItemEntity = null;
        if (cartItemEntities.size() == 1) {
            cartItemEntity = cartItemEntities.getFirst();
            cartItemEntity.setQuantity(cartItemRequest.getQuantity());
        } else {
            cartItemEntity = itemMapper.toCartItemEntity(cartItemRequest);
            cartItemEntity.setCart(cartEntity);
            cartEntity.getItemList().add(cartItemEntity);
        }
        cartRepository.save(cartEntity);
        return cartMapper.entityToResponse(cartEntity);
    }

    @Override
    public CartResponse removeItemFromCart(String userId, Long productId) throws ProductNotFoundException, CartNotFoundException {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseThrow(()-> new CartNotFoundException("Not found"));
        CartItemEntity cartItemEntity = cartEntity.getItemList().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new ProductNotFoundException("Not Found in Cart", productId));
        cartEntity.getItemList().remove(cartItemEntity);
        cartRepository.save(cartEntity);
        return cartMapper.entityToResponse(cartEntity);
    }

    @Override
    public CartDetailedResponse getDetailedCart(String userId) throws CartNotFoundException {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseThrow(()-> new CartNotFoundException("Not found"));

        List<Long> productIds = cartEntity.getItemList().stream().map(product -> product.getProductId()).toList();

        List<ProductDTO> productDTOList = productFeignClient.getAllById(productIds).getBody();

        CartDetailedResponse cartResponse = cartMapper.entityToDetailedResponse(cartEntity);
        cartResponse.setItemDetailedList(cartResponse.getItemDetailedList().stream().map(item -> {
            item.setProduct(
                    productDTOList.stream()
                            .filter(product -> item.getProductId().equals(product.getId()))
                            .toList()
                            .getFirst()
            );
            item.setTotalProduct(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return item;})
                .toList());
        cartResponse.setTotalCart(cartResponse.getItemDetailedList().stream()
                .map(item -> item.getTotalProduct())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return cartResponse;
    }
    @Override
    public CartResponse getBasicCart(String userId) throws CartNotFoundException {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseThrow(()-> new CartNotFoundException("Not found"));
        return cartMapper.entityToResponse(cartEntity);
    }
    @Override
    public BigDecimal getCartTotalPrice(String userId) throws CartNotFoundException {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseThrow(()-> new CartNotFoundException("Not found"));
        List<Long> productIds = cartEntity.getItemList()
                .stream()
                .map(product -> product.getProductId())
                .toList();

        List<ProductDTO> products = productFeignClient.getAllById(productIds).getBody();
        BigDecimal total = cartEntity.getItemList().stream()
                .map(item -> {
                    Optional<ProductDTO> productDTO = products.stream()
                            .filter(p -> p.getId().equals(item.getProductId()))
                            .findFirst();
                    return productDTO.map(p -> p.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).orElse(BigDecimal.ZERO);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }
}
