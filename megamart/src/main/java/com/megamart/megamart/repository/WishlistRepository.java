package com.megamart.megamart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.megamart.megamart.Entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByCustomerId(Long customerId);

    void deleteByCustomerIdAndProductId(Long customerId, Long productId);
}
