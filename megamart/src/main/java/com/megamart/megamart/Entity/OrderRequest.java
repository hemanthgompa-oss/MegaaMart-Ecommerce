package com.megamart.megamart.Entity;

import java.util.List;

public class OrderRequest {

    private Long customerId;
    private String deliveryAddress;
    private String paymentMethod;
    private List<OrderProductRequest> items;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderProductRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderProductRequest> items) {
        this.items = items;
    }
}