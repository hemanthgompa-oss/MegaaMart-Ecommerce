package com.megamart.megamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megamart.megamart.Entity.product;

@Repository
public interface ProductRepository extends JpaRepository<product, Long> {

}