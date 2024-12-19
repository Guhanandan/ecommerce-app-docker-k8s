package com.microservice.order_service;

public class OrderDetails {

    private OrderRequest orderRequest;
    private Product product;

    // Default constructor
    public OrderDetails() {}

    // Parameterized constructor
    public OrderDetails(OrderRequest orderRequest, Product product) {
        this.orderRequest = orderRequest;
        this.product = product;
    }

    // Getter and Setter for OrderRequest
    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    // Getter and Setter for Product
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderRequest=" + orderRequest +
                ", product=" + product +
                '}';
    }
}
