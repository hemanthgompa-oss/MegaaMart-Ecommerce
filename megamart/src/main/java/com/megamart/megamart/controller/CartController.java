package com.megamart.megamart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.megamart.megamart.Entity.Cart;
import com.megamart.megamart.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Cart addToCart(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        return cartService.addToCart(customerId, productId, quantity);
    }

    @GetMapping("/{customerId}")
    public List<Cart> getCart(@PathVariable Long customerId) {
        return cartService.getCart(customerId);
    }

    @DeleteMapping("/remove")
    public String removeFromCart(
            @RequestParam Long customerId,
            @RequestParam Long productId) {
        cartService.removeFromCart(customerId, productId);
        return "Product removed from cart";
    }
}