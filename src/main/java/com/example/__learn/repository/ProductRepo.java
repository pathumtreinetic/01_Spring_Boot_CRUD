package com.example.__learn.repository;

import com.example.__learn.Entity.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;

@Repository
public interface ProductRepo extends MongoRepository<Products, String> {
    boolean existsByProductName(String name);

    List<Products> findByProductNameContainingIgnoreCase(String name);

    //Option<Products> findByProductId(String productId);
}
