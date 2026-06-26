package com.example.__learn.repository;

import com.example.__learn.Entity.Carts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends MongoRepository<Carts, String> {
}
