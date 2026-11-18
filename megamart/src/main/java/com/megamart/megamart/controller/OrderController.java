package com.megamart.megamart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.megamart.megamart.Entity.CustomerOrder;
import com.megamart.megamart.Entity.OrderItem;
import com.megamart.megamart.Entity.OrderRequest;
import com.megamart.megamart.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public CustomerOrder placeOrder(@RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }

    @GetMapping("/customer/{customerId}")
    public List<CustomerOrder> getCustomerOrders(@PathVariable Long customerId) {
        return orderService.getCustomerOrders(customerId);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItem> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }

    @GetMapping("/{orderId}")
    public CustomerOrder getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}/status")
    public CustomerOrder updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }
}