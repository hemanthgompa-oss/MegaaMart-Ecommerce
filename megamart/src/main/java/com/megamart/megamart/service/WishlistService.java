package com.megamart.megamart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megamart.megamart.repository.WishlistRepository;
import com.megamart.megamart.Entity.Wishlist;
import com.megamart.megamart.repository.ProductRepository;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository,
                           ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public Wishlist addToWishlist(Long customerId, Long productId) {

        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlist = new Wishlist();
        wishlist.setCustomerId(customerId);
        wishlist.setProductId(productId);

        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> getWishlist(Long customerId) {
        return wishlistRepository.findByCustomerId(customerId);
    }

    @Transactional
    public void removeFromWishlist(Long customerId, Long productId) {
        wishlistRepository.deleteByCustomerIdAndProductId(customerId, productId);
    }
}