package com.megamart.megamart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.megamart.megamart.Entity.CustomerOrder;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCustomerId(Long customerId);
}