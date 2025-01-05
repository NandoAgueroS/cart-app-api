package com.cart_app.cart_service.repository;

import com.cart_app.cart_service.model.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ICartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserId(String userId);
}
