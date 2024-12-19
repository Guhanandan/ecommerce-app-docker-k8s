package com.microservice.order_service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "{product-service.url}")
public interface ProductServiceClient {

    @GetMapping("/products")
    List<Product> getAllProducts();

    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id") Long id);
}
