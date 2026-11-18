package com.megamart.megamart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megamart.megamart.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}