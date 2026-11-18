package com.megamart.megamart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megamart.megamart.Entity.CustomerOrder;
import com.megamart.megamart.Entity.OrderItem;
import com.megamart.megamart.Entity.OrderProductRequest;
import com.megamart.megamart.Entity.OrderRequest;
import com.megamart.megamart.Entity.product;
import com.megamart.megamart.repository.OrderRepository;
import com.megamart.megamart.repository.OrderItemRepository;
import com.megamart.megamart.repository.ProductRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CustomerOrder placeOrder(OrderRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Order must contain products");
        }

        if (request.getDeliveryAddress() == null ||
                request.getDeliveryAddress().isBlank()) {
            throw new RuntimeException("Delivery address is required");
        }

        double total = 0;

        List<OrderItem> items = new ArrayList<>();

        for (OrderProductRequest itemRequest : request.getItems()) {

            if (itemRequest.getQuantity() <= 0) {
                throw new RuntimeException("Invalid product quantity");
            }

            product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + product.getName());
            }

            double itemTotal = product.getPrice() * itemRequest.getQuantity();
            total += itemTotal;

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());

            items.add(orderItem);
        }

        CustomerOrder order = new CustomerOrder();
        order.setCustomerId(request.getCustomerId());
        order.setTotalAmount(total);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setOrderStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        CustomerOrder savedOrder = orderRepository.save(order);

        for (OrderItem item : items) {
            item.setOrderId(savedOrder.getId());
            orderItemRepository.save(item);
        }

        return savedOrder;
    }

    public List<CustomerOrder> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public CustomerOrder getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public CustomerOrder updateOrderStatus(Long orderId, String status) {
        CustomerOrder order = getOrderById(orderId);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
}