package com.megamart.megamart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.megamart.megamart.Entity.Wishlist;
import com.megamart.megamart.service.WishlistService;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "http://localhost:5173")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public Wishlist addToWishlist(
            @RequestParam Long customerId,
            @RequestParam Long productId) {
        return wishlistService.addToWishlist(customerId, productId);
    }

    @GetMapping("/{customerId}")
    public List<Wishlist> getWishlist(@PathVariable Long customerId) {
        return wishlistService.getWishlist(customerId);
    }

    @DeleteMapping("/remove")
    public String removeFromWishlist(
            @RequestParam Long customerId,
            @RequestParam Long productId) {
        wishlistService.removeFromWishlist(customerId, productId);
        return "Product removed from wishlist";
    }
}