package com.megamart.megamart.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.megamart.megamart.Entity.product;
import com.megamart.megamart.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public product addProduct(@RequestBody product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<product> getproductById(@PathVariable Long id) {
        Optional<product> product = productService.getproductById(id);

        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<product> updateProduct(
            @PathVariable Long id,
            @RequestBody product product) {

        Optional<product> existingProduct =
                productService.getproductById(id);

        if (existingProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        product.setId(id);

        product updatedProduct =
                productService.saveProduct(product);

        return ResponseEntity.ok(updatedProduct);
    }
}