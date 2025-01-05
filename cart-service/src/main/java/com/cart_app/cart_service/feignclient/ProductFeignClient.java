package com.cart_app.cart_service.feignclient;

import com.cart_app.cart_service.dto.ProductDTO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/products/{id}")
    ResponseEntity<ProductDTO> findOne(@PathVariable Long id);

    @GetMapping("/products/all-by-id")
    ResponseEntity<List<ProductDTO>> getAllById(@RequestParam List<Long> ids);
}
