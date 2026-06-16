package com.example.__learn.repository;

import com.example.__learn.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Products, Long> {
    boolean existsByName(String name);
}
