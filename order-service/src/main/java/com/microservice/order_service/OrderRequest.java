package com.microservice.order_service;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "orders")
public class OrderRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    // Default constructor
    public OrderRequest() {
    }

    // Constructor with all fields
    public OrderRequest(Long userId, Long productId , int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
