package com.megamart.megamart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.megamart.megamart.Entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByCustomerId(Long customerId);

    void deleteByCustomerIdAndProductId(Long customerId, Long productId);
}