package com.microservice.order_service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<OrderRequest, Long> {
    List<OrderRequest> findByUserId(Long userId);
}
