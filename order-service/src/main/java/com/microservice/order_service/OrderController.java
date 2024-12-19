package com.microservice.order_service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CustomerOrderRepository orderRepository;

    private ProductServiceClient productServiceClient;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println(orderRequest);
        Long userId = orderRequest.getUserId();
        Long productId = orderRequest.getProductId();
        int quantity = orderRequest.getQuantity();

        if (userId == null || productId == null) {
            return ResponseEntity.badRequest().body("userId and productId are required.");
        }

        // Business logic to handle the order, e.g., save it to the database
        try{
            OrderRequest newOrderRequest = new OrderRequest(userId, productId, quantity);
            System.out.println(newOrderRequest);
            orderRepository.save(newOrderRequest);
        }
        catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

        return ResponseEntity.ok("Order created successfully.");
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderRequest>> getOrders() {
        List<OrderRequest> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }


    // router to get all the orders for a user
    @GetMapping("/user/{userId}")
    public List<OrderRequest> getOrdersByUser(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }

    //
    @GetMapping("/{orderId}")
    public OrderDetails getOrderDetails(@PathVariable Long orderId) {
        OrderRequest order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id "+ orderId));
        Product product = productServiceClient.getProductById(order.getProductId());
        return new OrderDetails(order, product);
    }
}
