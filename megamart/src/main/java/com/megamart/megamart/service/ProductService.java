package com.megamart.megamart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.megamart.megamart.Entity.product;
import com.megamart.megamart.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public product saveProduct(product product) {
        return productRepository.save(product);
    }

    public List<product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<product> getproductById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}