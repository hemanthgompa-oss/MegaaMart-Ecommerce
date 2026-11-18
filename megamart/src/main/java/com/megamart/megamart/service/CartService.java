package com.megamart.megamart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megamart.megamart.Entity.Cart;
import com.megamart.megamart.Entity.product;
import com.megamart.megamart.repository.CartRepository;
import com.megamart.megamart.repository.ProductRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // ADD PRODUCT TO CART
    public Cart addToCart(Long customerId, Long productId, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        cart.setProductId(productId);
        cart.setQuantity(quantity);
        cart.setTotalPrice(product.getPrice() * quantity);

        return cartRepository.save(cart);
    }

    // VIEW CART
    public List<Cart> getCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }

    // REMOVE PRODUCT FROM CART
    @Transactional
    public void removeFromCart(Long customerId, Long productId) {
        cartRepository.deleteByCustomerIdAndProductId(customerId, productId);
    }
}