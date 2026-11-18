package com.megamart.megamart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.megamart.megamart.Entity.Customer;
import com.megamart.megamart.Entity.CustomerOrder;
import com.megamart.megamart.Entity.CustomerResponse;
import com.megamart.megamart.Entity.product;
import com.megamart.megamart.repository.CustomerRepository;
import com.megamart.megamart.repository.OrderRepository;
import com.megamart.megamart.repository.ProductRepository;
import com.megamart.megamart.service.OrderService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public AdminController(CustomerRepository customerRepository,
                           OrderRepository orderRepository,
                           ProductRepository productRepository,
                           OrderService orderService) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    // VIEW ALL CUSTOMERS
    @GetMapping("/customers")
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll().stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getFullName(),
                        customer.getEmail(),
                        customer.getMobileNumber(),
                        customer.getAddress()))
                .toList();
    }

    // VIEW ALL ORDERS
    @GetMapping("/orders")
    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    // VIEW ALL PRODUCTS
    @GetMapping("/products")
    public List<product> getAllProducts() {
        return productRepository.findAll();
    }

    // UPDATE ORDER STATUS
    @PutMapping("/orders/{orderId}/status")
    public CustomerOrder updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        return orderService.updateOrderStatus(orderId, status);
    }

    // SALES AND REVENUE
    @GetMapping("/dashboard")
    public String getDashboard() {

        long totalCustomers = customerRepository.count();
        long totalProducts = productRepository.count();
        long totalOrders = orderRepository.count();

        double totalRevenue = orderRepository.findAll()
                .stream()
                .mapToDouble(CustomerOrder::getTotalAmount)
                .sum();

        return "Total Customers: " + totalCustomers
                + ", Total Products: " + totalProducts
                + ", Total Orders: " + totalOrders
                + ", Total Revenue: " + totalRevenue;
    }
}