package com.example.__learn.repository;

import com.example.__learn.Entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends MongoRepository<Users, String> {
    @Override
    Optional<Users> findById(String aLong);

    boolean existsByName(String name);

    Optional<Users> findByName(String username);

    Optional<Users> findByEmail(String email);
}
